package org.olympics.domains

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/**
 * Olympic event at an Olympic game
 * Created by cjl7959 on 12/6/18.
 */
@NodeEntity
@ToString(includeNames = true, excludes = 'results')
@EqualsAndHashCode(includes = ['id'])
class Event {

  @Id
  @GeneratedValue
  Long id

  String name
  String sport //todo: implement later
  //Game game
  Integer year

  @Relationship(type = "PARTICIPATED_IN", direction = Relationship.INCOMING)
  List<Result> results = []

}
