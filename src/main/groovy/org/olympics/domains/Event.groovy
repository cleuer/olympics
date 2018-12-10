package org.olympics.domains

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/**
 * Olympic event at an Olympic game
 * Created by C. Leuer
 */
@ToString(includeNames = true, excludes = 'results')
@EqualsAndHashCode(includes = ['id'])
@NodeEntity
class Event {

  @Id
  @GeneratedValue
  Long id

  String name
  String sport
  Integer year

  //workaround because seems there is a Spring Data Neo4j BUG saving extra props on relationships
  String goldMedalist
  String silverMedalist
  String bronzeMedalist

  @JsonIgnoreProperties('results')
  @Relationship(type = "PARTICIPATED_IN", direction = Relationship.INCOMING)
  List<Result> results = []

  void addResult(Result result) {
    if (this.results == null) {
      this.results = new ArrayList<>();
    }
    this.results.add(result);
  }

}
