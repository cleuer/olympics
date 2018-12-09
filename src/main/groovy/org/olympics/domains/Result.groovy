package org.olympics.domains

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.neo4j.ogm.annotation.*

/**
 * Individual athletes result in an event
 * Created by C. Leuer
 */
@ToString(includeNames = true)
@RelationshipEntity(type = "PARTICIPATED_IN")
class Result {

  Long id  //id is unique value from input csv lines

  Medal medal

  @StartNode
  Athlete athlete

  @EndNode
  Event event
}
