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

  @GetMapping('/graph')
  @ResponseStatus(HttpStatus.OK)
  String getSuspectConditions(@RequestParam(value='year', required = true) Integer year,
                              @RequestParam(value='season', required = true) String season) {
    log.info("GET graph request for $year $season olympics")
    graphService.getGraphByYearAndSeason(year, season)
  }

}
