package fr.dauphine.trip.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextStepDTO {

    @JsonProperty("city_name")
    private String city;

    @JsonProperty("distance")
    private BigDecimal distance;

    @JsonProperty("travel_time")
    private Duration travelTime;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NextStepDTO(
            @JsonProperty(value = "city_name", required = true) String city,
            @JsonProperty(value = "distance", required = true) BigDecimal distance,
            @JsonProperty(value = "travel_time", required = true) Duration travelTime
    ) {
        this.city = city;
        this.distance = distance;
        this.travelTime = travelTime;
    }

    public static NextStepDTOBuilder builder() {
        return new NextStepDTOBuilder();
    }

    public static class NextStepDTOBuilder {
        private String city;
        private BigDecimal distance;
        private Duration travelTime;

        public NextStepDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public NextStepDTOBuilder distance(BigDecimal distance) {
            this.distance = distance;
            return this;
        }

        public NextStepDTOBuilder travelTime(Duration travelTime) {
            this.travelTime = travelTime;
            return this;
        }

        public NextStepDTO build() {
            return new NextStepDTO(city, distance, travelTime);
        }
    }
}
