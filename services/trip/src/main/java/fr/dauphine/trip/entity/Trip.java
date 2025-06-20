package fr.dauphine.trip.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trip")
public class Trip {

    @Id
    private String id;

    @Field("reference")
    private String reference;

    @Field("start_date")
    private OffsetDateTime startDate;

    @Field("end_date")
    private OffsetDateTime endDate;

    @Field("steps")
    private List<Step> steps;

}
