package org.olympics.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration for custom properties for Olympics app
 * Created by cjl7959 on 12/7/18.
 */
@ConfigurationProperties('olympics')
class OlympicsConfig {

  String dataFileName
  Boolean loadData
}
