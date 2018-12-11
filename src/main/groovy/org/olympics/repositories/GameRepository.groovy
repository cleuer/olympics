package org.olympics.repositories

import org.olympics.domains.Game
import org.springframework.data.neo4j.annotation.Depth
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Game Repository with REST enabled
 * Created by C. Leuer
 */

@RepositoryRestResource(collectionResourceRel = "games", path = "games")
interface GameRepository extends Neo4jRepository<Game, Long> {

  Game findOneByName(@Param("name") String name)

  @Depth(4)
  Game findOneByYearAndSeason(@Param("year") Integer year, @Param("season") String season)
  //todo: get game by year
//  @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
//  Game gameGraphByYear(@Param("year") Integer year);
}
