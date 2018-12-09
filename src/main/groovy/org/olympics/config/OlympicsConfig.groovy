package org.olympics.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration for custom properties for Olympics app
 * Created by C. Leuer
 */
@ConfigurationProperties('olympics')
class OlympicsConfig {

  String dataFileName
  Boolean loadData
}
