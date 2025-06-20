package fr.dauphine.roadsservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCityDTO {
    private Integer id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}