package fr.dauphine.trip.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripSummary {

    @JsonProperty("reference")
    private final String reference;

    @JsonProperty("trip")
    private final TripCreationRequest trip;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TripSummary(
            @JsonProperty(value = "reference", required = true) String reference,
            @JsonProperty(value = "trip", required = true) TripCreationRequest trip
    ) {
        this.trip = trip;
        this.reference = reference;
    }

}
