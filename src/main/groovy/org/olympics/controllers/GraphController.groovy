package org.olympics.controllers

import groovy.util.logging.Slf4j
import org.olympics.services.GraphService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Olympics controller for GET requests for Graph JSON for nodes and links
 * Created by C. Leuer
 */

@RestController
@Slf4j
class GraphController {

  @Autowired
  GraphService graphService

  private static final String MEDAL_FILTER_OFF = 'off'

  @GetMapping('/graph')
  @ResponseStatus(HttpStatus.OK)
  String getGraphByYearAndSeason(@RequestParam(value='year', required = true) Integer year,
                                 @RequestParam(value='season', required = true) String season,
                                 @RequestParam(value='sport', required = false) String sport,
                                 @RequestParam(value='gold', defaultValue = MEDAL_FILTER_OFF) String gold,
                                 @RequestParam(value='silver', defaultValue = MEDAL_FILTER_OFF) String silver,
                                 @RequestParam(value='bronze', defaultValue = MEDAL_FILTER_OFF) String bronze) {
    log.info("Get graph request for $year $season olympics. filter by: $sport $gold $silver $bronze")
    graphService.getGraphByYearAndSeason(year, season, sport, gold, silver, bronze)
  }
}
