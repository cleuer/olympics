package org.olympics.services

import groovy.util.logging.Slf4j
import org.olympics.beans.SportGroups
import org.olympics.data.InputLine
import org.olympics.domains.*
import org.olympics.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

  @Autowired
  ResultRepository resultRepository

  @Autowired
  SportGroups sportGroups

  //private static final Integer DEPTH_FOUR = 4
  /**
   * Idempotent persistence of Games, Events and Athletes for a single line of input
   * @param input
   */
  @Transactional
  void loadOlympicData(InputLine input) {

    Game game = getGame(input)
    Event event = getEvent(input, game)
    Athlete athlete = getAthlete(input, event)

    Result result = getResult(input, athlete, event)

    event.results.removeAll { r -> r.athlete == result.athlete && r.event == result.event }
    event.addResult(result)

    log.info "save athlete: $athlete"
    athleteRepository.save(athlete)

    log.info "update event with results: $event"
    eventRepository.save(event)

    game.events.remove(event)
    game.events.add(event)

    Game savedGame = gameRepository.save(game)
    log.info "saved game: ${savedGame.name}, event: ${event.name}  and athlete: ${athlete.name}"

  }

  /**
   * Get new Game if not found, or udpate existing Game
   * @param input
   * @return Game
   */
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

  /**
   * Get new Event if not found, or update existing Event
   * @param input
   * @param game
   * @return
   */
  Event getEvent(InputLine input, Game game) {
    Event event = eventRepository.findOneByNameAndYear(input.event, input.year)
    if (event) {
      log.info("event found for event name: ${input.event} and year: ${input.year}")
    } else {
      log.info("event not found for event: ${input.event} and year: ${input.year}")
      event = new Event()
    }
    event = getMedalist(event, input)
    event.with {
      name = input.event
      sport = input.sport
      year = input.year
      sportGroup = sportGroups.getGroup(sport)
      it
    }
  }

  /**
   * Get new athlete if not found or update existing Event
   * @param input
   * @param event
   * @return Athlete
   */
  Athlete getAthlete(InputLine input, Event event) {
    Athlete athlete = athleteRepository.findOneByNameAndSexAndCountryAbbr(input.name, getSex(input), input.noc)
    if (athlete) {
      log.info "athlete found for ${input.name}"
    } else {
      log.info("athlete not found by name: ${input.name}")
      athlete = new Athlete()
    }
    //todo: add back
    athlete.events.add(event)
    athlete.with {
      name = input.name
      age = input.age
      sex = getSex(input)
      country = input.team
      countryAbbr = input.noc
      height = input.height
      weight = input.weight
      it
    }
  }
/**
 * Get Result for an athlete in an event
 * @param input
 * @param athlete
 * @param event
 * @return Result
 */

  Result getResult(InputLine input, Athlete athlete, Event event) {
    new Result().with {
      id = input.id
      it.athlete = athlete
      it.event = event
      it.medal = input.medal
      it
    }
  }

  String getSex(InputLine input) {
    input.sex == 'F' ? 'Female' : 'Male'
  }
  /**
   * Assign medalist to event
   * @param event
   * @param input
   * @return
   */
  Event getMedalist(Event event, InputLine input) {
    if (input.medal == Medal.Gold.toString()) {
      event.goldMedalist = input.name
    } else if (input.medal == Medal.Silver.toString()) {
      event.silverMedalist = input.name
    } else if (input.medal == Medal.Bronze.toString()) {
      event.bronzeMedalist = input.name
    }
    event
  }

}
