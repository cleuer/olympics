package org.olympics.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration for custom properties for Olympics app
 * Created by C. Leuer
 */
@ConfigurationProperties('olympics')
class OlympicsConfig {

  //although there is one athlete, graph will include a unique athlete node for each event
  Boolean graphDetachAthletes
  String dataFileName
  Boolean loadData
}
