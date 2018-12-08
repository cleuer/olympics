package org.olympics.services

import groovy.util.logging.Slf4j
import org.olympics.domains.*
import org.olympics.repositories.*
import org.springframework.stereotype.Service
import groovy.json.JsonBuilder


/**
 * Service to support graph endpoints
 * Created by cjl7959 on 12/7/18.
 */

@Slf4j
@Service
class OlympicsService {

  static final String HOST = 'HOST'
  static final String PARTICIPATED_IN = 'PARTICIPATED_IN'

  String getGraphByGameName(String gameName) {
    //todo
    ''

  }

  String buildGraphForGame(Game game) {

    List<Map<String, Object>> nodes
    List<Map<String, Object>> links
    (nodes, links) = getNodesAndLinks(game)

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
  Tuple getNodesAndLinks(Game game) {

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
      int eventIndex = i
      nodes << [
          type: Event.simpleName,
          index: eventIndex,
          name: event.name
      ]
      i++

    //3 add game -> HOST -> event link
      links << [
          type: HOST,
          source: gameIndex,
          target: eventIndex
      ]

     //4. add event nodes
      event.results.each { result ->
        int athleteIndex = i
        Athlete athlete = result.athlete
        nodes << [
            type: Athlete.simpleName,
            index: athleteIndex,
            name: athlete.name,
            country: athlete.country
        ]
        i++

     //5. add athlete -> PARTICIPATED_IN -> event link
        links << [
            type: PARTICIPATED_IN,
            source: athleteIndex,
            target: eventIndex,
            medal: result.medal
        ]
      }
    }
    log.info "getNodesAndLinks() nodes found: $i"
    new Tuple(nodes, links)
  }

}
