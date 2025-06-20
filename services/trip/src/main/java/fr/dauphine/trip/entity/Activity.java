package fr.dauphine.trip.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Field("name")
    private String name;

    @Field("price")
    private BigDecimal price;

    @Field("poi_name")
    private String poiName;

    @Field("date")
    private OffsetDateTime date;

}
