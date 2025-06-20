package fr.dauphine.trip.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StepDTO {

    @JsonProperty("start_date")
    private final OffsetDateTime startDate;

    @JsonProperty("end_date")
    private final OffsetDateTime endDate;

    @JsonProperty("city_name")
    private final String city;

    @JsonProperty("activities")
    private List<ActivityDTO> activities;

    @JsonProperty("lodging")
    private LodgingDTO lodging;

    @JsonProperty("next_step")
    private NextStepDTO nextStepDTO;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StepDTO(
            @JsonProperty(value = "city_name", required = true) String city,
            @JsonProperty(value = "lodging", required = true) LodgingDTO lodging,
            @JsonProperty(value = "end_date", required = true) OffsetDateTime endDate,
            @JsonProperty(value = "next_step", required = true) NextStepDTO nextStepDTO,
            @JsonProperty(value = "start_date", required = true) OffsetDateTime startDate,
            @JsonProperty(value = "activities", required = true) List<ActivityDTO> activities
    ) {
        this.city = city;
        this.lodging = lodging;
        this.endDate = endDate;
        this.startDate = startDate;
        this.activities = activities;
        this.nextStepDTO = nextStepDTO;
    }

    public static StepDTOBuilder builder() {
        return new StepDTOBuilder();
    }

    public static class StepDTOBuilder {
        private List<ActivityDTO> activities;
        private OffsetDateTime startDate;
        private NextStepDTO nextStepDTO;
        private OffsetDateTime endDate;
        private LodgingDTO lodging;
        private String city;

        public StepDTOBuilder activities(List<ActivityDTO> activities) {
            this.activities = activities;
            return this;
        }

        public StepDTOBuilder nextStep(NextStepDTO nextStepDTO) {
            this.nextStepDTO = nextStepDTO;
            return this;
        }

        public StepDTOBuilder startDate(OffsetDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public StepDTOBuilder endDate(OffsetDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public StepDTOBuilder lodging(LodgingDTO lodging) {
            this.lodging = lodging;
            return this;
        }

        public StepDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public StepDTO build() {
            return new StepDTO(city, lodging, endDate, nextStepDTO, startDate, activities);
        }
    }
}
