package org.olympics.repositories

import org.olympics.domains.Event
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Events repository with REST enabled
 * Created by cjl7959 on 12/6/18.
 */
@RepositoryRestResource(collectionResourceRel = "events", path = "events")
interface EventRepository extends Neo4jRepository<Event, Long> {

  Event findOneByNameAndYear(@Param("name") String name, @Param("year") Integer year)
}
