package org.olympics.domains

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/**
 * Athlete who participated in Olympic event
 * Created by cjl7959 on 12/1/18.
 */
@NodeEntity
@ToString(includeNames = true, excludes = 'events')
@EqualsAndHashCode(includes = ['name', 'sex', 'countryAbbr'])
class Athlete {

  @Id
  @GeneratedValue
  Long id

  String name
  String sex
  Integer age
  Integer height
  Integer weight
  String country
  String countryAbbr

  @Relationship(type = "PARTICIPATED_IN")
  List<Event> events = []

}
