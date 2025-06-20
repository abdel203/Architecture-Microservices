package fr.dauphine.trip.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LodgingDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("address")
    private String address;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public LodgingDTO(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "price", required = true) BigDecimal price,
            @JsonProperty(value = "address", required = true) String address
    ) {
        this.name = name;
        this.price = price;
        this.address = address;
    }

    public static LodgingDTOBuilder builder() {
        return new LodgingDTOBuilder();
    }

    public static class LodgingDTOBuilder {
        private String     name;
        private BigDecimal price;
        private String     address;

        public LodgingDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LodgingDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public LodgingDTOBuilder address(String address) {
            this.address = address;
            return this;
        }

        public LodgingDTO build() {
            return new LodgingDTO(name, price, address);
        }
    }
}
