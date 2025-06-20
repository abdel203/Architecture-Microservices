package fr.dauphine.trip.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lodging {

    @Field("name")
    private String name;

    @Field("price")
    private BigDecimal price;

    @Field("address")
    private String address;

}
