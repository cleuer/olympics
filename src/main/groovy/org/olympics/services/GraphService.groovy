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

  static final String HOST = 'HOST'
  static final String PARTICIPATED_IN = 'PARTICIPATED_IN'
  static final String EMPTY_JSON = '{}'

  /**
   *
   * @param year
   * @param season
   * @param sport
   * @return
   */
  @Transactional(readOnly = true)
  String getGraphByYearAndSeason(Integer year, String season, String sport) {
    Game game = gameRepository.findOneByYearAndSeason(year, season)
    if (game) {
      log.info "found game: ${game.name} hosted in city: ${game.city}"
      buildGraphForGame(game, sport)
    } else {
      log.info "could not find game for year: $year and season: $season"
      EMPTY_JSON
    }
  }

  String buildGraphForGame(Game game, String sport) {

    log.info "build graph for game: $game"

    List<Map<String, Object>> nodes
    List<Map<String, Object>> links
    (nodes, links) = getNodesAndLinks(game, sport)

    new JsonBuilder(
        [
            nodes : nodes,
            links : links
        ]
    ).toString()
  }

  /**
   * Build map of two deep elements containing nodes and links
   * @param game
   * @return
   */
  Tuple getNodesAndLinks(Game game, String sport) {

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
            sportGroup : event.sportGroup
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
          int athleteIndex = i
          Athlete athlete = result.athlete
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

    log.info "getNodesAndLinks() nodes found: $i"
    new Tuple(nodes, links)
  }

  /**
   * get medal for link
   * @param event
   * @param athlete
   * @return Medal
   */
  private String getMedal(Event event, Athlete athlete) {
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
   * @return
   */
  private Boolean includeEvent (Event event, String sport) {
    if (sport) {
      sport.toLowerCase() == event.sport?.toLowerCase()
    } else {
      true
    }
  }

}
