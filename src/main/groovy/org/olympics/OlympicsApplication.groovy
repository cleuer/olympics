package org.olympics

import org.olympics.config.OlympicsConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

/**
 * Olympics Application is web application to represent Olympic History as Graph.
 *
 * Features include:
 * 1. Load 120 years of Olympic History results into graph database Neo4j
 * 2. Support REST interactions with Games, Events and Athletes
 * 3. Display Graph Visualizations of Olympic History leveragng d3.js
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(OlympicsConfig)
class OlympicsApplication {

	static void main(String[] args) {
		SpringApplication.run OlympicsApplication, args
	}
}
