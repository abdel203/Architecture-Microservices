package fr.dauphine.roadsservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoadDTO {
    private Integer fromCityId;
    private Integer toCityId;
    private Double distance;
    private String duration;
}
