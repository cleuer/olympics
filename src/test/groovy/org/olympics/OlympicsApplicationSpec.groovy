package org.olympics

import org.olympics.domains.*
import org.olympics.services.GraphService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = OlympicsApplication)
class OlympicsApplicationSpec extends Specification {

	@Autowired
	GraphService olympicsService

	void 'json builds for graph'() {

		given: 'a linked game, event, result and athlete'
		Game game = new Game(id: 0L, name: '1984 Summer', season: 'Summer', city: 'Los Angeles')
		String sport = "Swimming"
		Event event = new Event(id: 1L, name: "Swimming Men's 1,500 metres Freestyle", sport: sport, sportGroup: 2)
		Athlete athlete = new Athlete(id:2L, name: 'Rainer Henkel', country: 'West Germany', sex: 'Male')
		Result result = new Result(id: 3L, athlete: athlete, event: event, medal: 'Silver')
		event.addResult(result)
		game.events.add(event)

		when: 'build graph for game'
		String retval = olympicsService.buildGraphForGame(game, sport, null, null, null)

		then: 'json has correct nodes and links'
		retval.replaceAll("\\s+","") == getClass().getResourceAsStream('/sampleGraph.json').text.replaceAll("\\s+","")
	}

}
