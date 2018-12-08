package org.olympics.domains

import groovy.transform.ToString
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

import java.beans.EventSetDescriptor

/**
 * Sport as a category i.e. Swimming, Sailing, Diving, Speed Skating
 * Created by cjl7959 on 12/6/18.
 *
 * todo: unused
 */
@NodeEntity
@ToString(includeNames = true, excludes = 'events')
class Sport {

  @Id
  @GeneratedValue
  private Long id

  String name

  @Relationship(type = "COMPETITION", direction = Relationship.UNDIRECTED)
  Set<Event> events
}
