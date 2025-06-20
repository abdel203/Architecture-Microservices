package fr.dauphine.trip.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("poi_name")
    private String poiName;

    @JsonProperty("date")
    private OffsetDateTime date;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ActivityDTO(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "price", required = true) BigDecimal price,
            @JsonProperty(value = "poi_name", required = true) String poiName,
            @JsonProperty(value = "date", required = true) OffsetDateTime date
    ) {
        this.name = name;
        this.price = price;
        this.poiName = poiName;
        this.date = date;
    }

    public static ActivityDTOBuilder builder() {
        return new ActivityDTOBuilder();
    }

    public static class ActivityDTOBuilder {
        private String name;
        private BigDecimal price;
        private String poiName;
        private OffsetDateTime date;

        public ActivityDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ActivityDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ActivityDTOBuilder poiName(String poiName) {
            this.poiName = poiName;
            return this;
        }

        public ActivityDTOBuilder date(OffsetDateTime date) {
            this.date = date;
            return this;
        }

        public ActivityDTO build() {
            return new ActivityDTO(name, price, poiName, date);
        }
    }

}
