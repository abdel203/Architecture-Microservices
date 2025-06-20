package fr.dauphine.trip.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NextStep {

    @Field("city_name")
    private String city;

    @Field("distance")
    private BigDecimal distance;

    @Field("travel_time")
    private Duration travelTime;

}
