package fr.dauphine.roadsservice.entity;

import lombok.*;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("City")
public class CityNode {

    @Id
    private Integer id;

    private String name;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Relationship(type = "ROAD_TO", direction = Relationship.Direction.OUTGOING)
    private List<Road> roads;

}
