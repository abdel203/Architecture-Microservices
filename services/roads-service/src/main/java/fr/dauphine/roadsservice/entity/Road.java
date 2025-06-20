package fr.dauphine.roadsservice.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class Road {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private CityNode destination;
    private Double distance; // km
    private String duration; //  minutes
}

