package org.olympics.services

import groovy.util.logging.Slf4j
import org.olympics.domains.*
import org.olympics.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import groovy.json.JsonBuilder
import org.springframework.transaction.annotation.Transactional


/**
 * Service to support graph endpoints
 * See src/resources/main/sampleGraph.json for example of output
 * Created by C. Leuer
 */

@Slf4j
@Service
class GraphService {

  @Autowired
  GameRepository gameRepository

  @Autowired
  EventRepository eventRepository

  private static final String HOST = 'HOST'
  private static final String PARTICIPATED_IN = 'PARTICIPATED_IN'
  private static final String EMPTY_JSON = '{}'
  private static final String ON = 'on'
  private static final String OFF = 'off'

  /**
   *
   * @param year
   * @param season
   * @param sport
   * @return
   */
  @Transactional(readOnly = true)
  String getGraphByYearAndSeason(Integer year, String season, String sport, String gold, String silver,
                                 String bronze) {
    Game game = gameRepository.findOneByYearAndSeason(year, season)
    if (game) {
      log.info "found game: ${game.name} hosted in city: ${game.city}"
      buildGraphForGame(game, sport, gold, silver, bronze)
    } else {
      log.info "could not find game for year: $year and season: $season"
      EMPTY_JSON
    }
  }

  /**
   * Build a graph of an Olympics with a game node and many event nodes linked by result to athlete nodes
   *  events can be filtered by sport ("Swimming", "Fencing") and/or athletes can be filtered by medal
   * @param game
   * @param sport
   * @param gold
   * @param silver
   * @param bronze
   * @return graph of nodes and links
   */
  String buildGraphForGame(Game game, String sport, String gold, String silver,
                           String bronze) {

    log.info "build graph for game: $game"

    List<Map<String, Object>> nodes
    List<Map<String, Object>> links
    (nodes, links) = getNodesAndLinks(game, sport, gold, silver, bronze)

    new JsonBuilder(
        [
            nodes : nodes,
            links : links
        ]
    ).toString()
  }

  /**
   * Build map of two deep elements containing nodes and links optimized for d3.js force library
   * @param game
   * @return Tuple of nodes and links
   */
  Tuple getNodesAndLinks(Game game, String sport,  String gold, String silver,
                         String bronze) {

    List<Map<String, Object>> nodes = []
    List<Map<String, Object>> links = []
    int i = 0 //index of nodes

    //1. add game node
    int gameIndex = i
    nodes << [
        type: Game.simpleName,
        index: gameIndex,
        gameName: game.name,
        season: game.season,
        city: game.city
    ]
    i++  //advance index for each node

    //2. add event nodes
    game.events.each { event ->
      if (includeEvent(event, sport)) {
        int eventIndex = i
        nodes << [
            type : Event.simpleName,
            index: eventIndex,
            name : event.name,
            sport : event.sport,
            sportGroup : event.sportGroup,
            goldMedalist: event.goldMedalist,
            silverMedalist: event.silverMedalist,
            bronzeMedalist: event.bronzeMedalist
        ]
        i++

        //3 add game -> HOST -> event link
        links << [
            type  : HOST,
            source: gameIndex,
            target: eventIndex
        ]

//     4. add event nodes
        List<Result> results = event?.results

        results.each { result ->
          Athlete athlete = result.athlete
          if (includeAthlete(event, athlete, gold, silver, bronze)) {
            int athleteIndex = i
            nodes << [
                type   : Athlete.simpleName,
                index  : athleteIndex,
                name   : athlete.name,
                country: athlete.country,
                sex    : athlete.sex
            ]
            i++

            //5. add athlete -> PARTICIPATED_IN -> event link
            links << [
                type  : PARTICIPATED_IN,
                source: athleteIndex,
                target: eventIndex,
                medal : getMedal(event, athlete)
            ]
          }
        }
      }
    }

    log.info "getNodesAndLinks() nodes found: $i"
    new Tuple(nodes, links)
  }

  /**
   * get medal for link
   * @param event
   * @param athlete
   * @return Medal
   */
  private Medal getMedal(Event event, Athlete athlete) {
    if (event.goldMedalist == athlete.name) {
      Medal.Gold
    } else if (event.silverMedalist == athlete.name) {
      Medal.Silver
    } else if (event.bronzeMedalist == athlete.name) {
      Medal.Bronze
    }
  }

  /**
   * Return true if filter sport matches event sport Or sport filter is null
   * @param event
   * @param sport
   * @return Boolean to include event
   */
  private Boolean includeEvent (Event event, String sport) {
    if (sport) {
      sport.toLowerCase() == event.sport?.toLowerCase()
    } else {
      true
    }
  }

  /**
   * Include athletes by medal filters. if no medal filters for gold, silver or bronze, then include all
   * athletes
   * @param event
   * @param athlete
   * @param gold
   * @param silver
   * @param bronze
   * @return Boolean to include athlete
   */
  private Boolean includeAthlete (Event event, Athlete athlete, String gold, String silver, String bronze) {
    ((gold == ON  && getMedal(event, athlete) == Medal.Gold) ||
       (silver == ON && getMedal(event, athlete) == Medal.Silver) ||
           (bronze == ON && getMedal(event, athlete) == Medal.Bronze) ||
        (gold == OFF && silver == OFF && bronze == OFF)
    )
  }

}
