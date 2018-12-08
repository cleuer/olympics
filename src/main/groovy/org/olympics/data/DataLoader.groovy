package org.olympics.data

import groovy.util.logging.Slf4j
import org.olympics.config.OlympicsConfig
import org.olympics.domains.*
import org.olympics.repositories.*
import org.olympics.services.LoadDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

/**
 *  Load Olympic History Data
 *  Name of data file can be change int application.properties to use smaller datasets
 */
@Component
@Slf4j
class DataLoader implements ApplicationRunner {

  @Autowired
  LoadDataService loadDataService

  @Autowired
  AthleteRepository athleteRepository

  @Autowired
  EventRepository eventRepository

  @Autowired
  GameRepository gameRepository

  @Autowired
  OlympicsConfig olympicsConfig

  void run(ApplicationArguments args) {
    if (olympicsConfig.loadData) {

     String dataFileName = olympicsConfig.dataFileName
     log.info "load data from resource file: $dataFileName"
     InputStream inputStream = getClass().getResourceAsStream("/$dataFileName")
      int l = 0
      new InputStreamReader(inputStream).eachLine { line ->
        if (l > 0) {
          InputLine input = new InputLine(line)
          log.info "$input"
          if (input.gameName == '1984 Summer' && input.sport == 'Swimming') {
            loadDataService.loadOlympicData(input)
          }

        }
        l++
      }
      log.info "lines read: ${l-1}"

      Integer gameCount = gameRepository.count()
      Integer eventCount = eventRepository.count()
      Integer athleteCount = athleteRepository.count()

      log.info "games: $gameCount, events: $eventCount, athletes: $athleteCount"
    }
  }
}

