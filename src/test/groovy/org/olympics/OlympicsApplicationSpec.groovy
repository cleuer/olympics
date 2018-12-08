package org.olympics

import org.olympics.domains.*
import org.olympics.services.OlympicsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = OlympicsService)
class OlympicsApplicationSpec extends Specification {

	@Autowired
	OlympicsService olympicsService

	void 'json builds for graph'() {

		given: 'a linked game, event, result and athlete'
		Game game = new Game(id: 0L, name: '1984 Summer', season: 'Summer', city: 'Los Angeles')
		Event event = new Event(id: 1L, name: "Swimming Men's 1,500 metres Freestyle")
		Athlete athlete = new Athlete(id:2L, name: 'Rainer Henkel', country: 'West Germany')
		Result result = new Result(id: 3L, athlete: athlete, event: event, medal: Medal.Silver)
		event.results.add(result)
		game.events.add(event)

		when: 'build graph for game'
		String retval = olympicsService.buildGraphForGame(game)

		then: 'json has correct nodes and links'
		retval.replaceAll("\\s+","") == getClass().getResourceAsStream('/sampleGraph.json').text.replaceAll("\\s+","")
	}

}
