package org.olympics.domains

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/**
 * Olympic game
 * Created by cjl7959 on 12/6/18.
 */

@NodeEntity
@ToString(includeNames = true, excludes = 'events')
@EqualsAndHashCode(includes = ['id'])
class Game {

  @Id
  @GeneratedValue
  Long id

  String name
  String city
  Integer year
  String season

  @Relationship(type = "HOST", direction = Relationship.UNDIRECTED)
  Set<Event> events =[]
}

