package org.olympics.services

import groovy.util.logging.Slf4j
import org.olympics.data.InputLine
import org.olympics.domains.*
import org.olympics.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Load Olymic data service
 * Created by cjl7959 on 12/7/18.
 */

@Slf4j
@Service
class LoadDataService {

  @Autowired
  AthleteRepository athleteRepository

  @Autowired
  EventRepository eventRepository

  @Autowired
  GameRepository gameRepository

  void loadOlympicData(InputLine input) {

    Game game = getGame(input)
    Event event = getEvent(input, game)
    Athlete athlete = getAthlete(input, event)
    game.events.add(event)
    Result result = getResult(input, athlete, event)
    event.results.add(result)

    log.info "save game: $game"
    gameRepository.save(game)

    log.info "save event: $event"
    eventRepository.save(event)

    log.info "save athlete: $athlete"
    athleteRepository.save(athlete)
  }

  Game getGame(InputLine input) {

    Game game = gameRepository.findOneByName(input.gameName)
    if (game) {
      log.info("game found: ${input.gameName}")
    } else {
      log.info("game not found: ${input.gameName}")
      game = new Game()
    }
    game.with {
      name = input.gameName
      city = input.city
      year = input.year
      season = input.season
      it
    }
  }

  Event getEvent(InputLine input, Game game) {
    Event event = eventRepository.findOneByNameAndYear(input.event, input.year)
    if (event) {
      log.info("event found for event name: ${input.event} and year: ${input.year}")
    } else {
      log.info("event not found for event: ${input.event} and year: ${input.year}")
      event = new Event()
    }
//    event.game = game
    event.with {
      name = input.event
      sport = input.sport
      year = input.year
      it
    }
  }

  Athlete getAthlete(InputLine input, Event event) {
    Athlete athlete = athleteRepository.findOneByNameAndSexAndCountryAbbr(input.name, input.sex, input.noc)
    if (athlete) {
      log.info "athlete found for ${input.name}"
    } else {
      log.info("athlete not found by name: ${input.name}")
      athlete = new Athlete()
    }
    athlete.events.add(event)
    athlete.with {
      name = input.name
      age = input.age
      sex = input.sex
      country = input.team
      countryAbbr = input.noc
      height = input.height
      weight = input.weight
      it
    }
  }

  Result getResult(InputLine input, Athlete athlete, Event event) {
    new Result().with {
      id = input.id
      it.athlete = athlete
      it.event = event
      medal = input.medal
      it
    }
  }

}
