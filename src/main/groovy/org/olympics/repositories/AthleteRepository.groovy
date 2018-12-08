package org.olympics.repositories

import org.olympics.domains.Athlete
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Athlete repository
 * Created by cjl7959 on 12/6/18.
 */
@RepositoryRestResource(collectionResourceRel = "athletes", path = "athletes")
interface AthleteRepository extends Neo4jRepository<Athlete, Long> {

  Athlete findOneByNameAndSexAndCountryAbbr(@Param("name") String name, @Param("sex") String sex,
                                            @Param("countryAbbr") String countryAbbr)
}
