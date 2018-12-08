package org.olympics.domains

import groovy.transform.ToString
import org.neo4j.ogm.annotation.*

/**
 * Individual athletes result in an event
 * Created by cjl7959 on 12/6/18.
 */
@RelationshipEntity(type = "PARTICIPATED_IN")
@ToString(includeNames = true)
class Result {

  Long id  //id is unique value from input csv lines

  Medal medal

  @StartNode
  Athlete athlete

  @EndNode
  Event event
}
