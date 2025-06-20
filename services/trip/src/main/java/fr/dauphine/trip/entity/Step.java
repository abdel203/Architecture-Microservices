package fr.dauphine.trip.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Step {

    @Field("start_date")
    private OffsetDateTime startDate;

    @Field("end_date")
    private OffsetDateTime endDate;

    @Field("city_name")
    private String city;

    @Field("activities")
    private List<Activity> activities;

    @Field("lodging")
    private Lodging lodging;

    @Field("next_step")
    private NextStep nextStep;

}
