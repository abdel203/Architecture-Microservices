package fr.dauphine.trip.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripCreationRequest {

    @JsonProperty("start_date")
    private final OffsetDateTime startDate;

    @JsonProperty("end_date")
    private final OffsetDateTime endDate;

    @JsonProperty("steps")
    private final List<StepDTO> steps;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TripCreationRequest(
            @JsonProperty(value = "steps", required = true)List<StepDTO> steps,
            @JsonProperty(value = "end_date", required = true) OffsetDateTime endDate,
            @JsonProperty(value = "start_date", required = true) OffsetDateTime startDate
    ) {
        this.steps = steps;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<StepDTO> steps;
        private OffsetDateTime endDate;
        private OffsetDateTime startDate;

        public Builder steps(List<StepDTO> steps) {
            this.steps = steps;
            return this;
        }

        public Builder startDate(OffsetDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(OffsetDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public TripCreationRequest build() {
            return new TripCreationRequest(steps, endDate, startDate);
        }

    }

}
