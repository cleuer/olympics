package org.olympics.repositories

import org.olympics.domains.Result
import org.springframework.data.neo4j.repository.Neo4jRepository


/**
 * Result repository
 * Created by c. leuer
 *
 */
interface ResultRepository extends Neo4jRepository<Result, Long> {
}