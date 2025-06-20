package fr.dauphine.roadsservice.repository;

import fr.dauphine.roadsservice.entity.CityNode;
import fr.dauphine.roadsservice.entity.Road;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityNodeRepository extends Neo4jRepository<CityNode, Integer> {

    Optional<CityNode> findByName(String name);

    @Query("""
        MATCH path = (start:City)-[:ROAD_TO*]->(end:City)
        WHERE start.id = $startId AND end.id = $endId
        RETURN [n IN nodes(path) | n] AS cities
    """)
    List<CityNode> findAllPathsBetween(@Param("startId") Integer startId, @Param("endId") Integer endId);

}

