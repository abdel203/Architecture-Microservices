package fr.dauphine.information.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoadNeoDTO {
    private Integer fromCityId;
    private Integer toCityId;
    private Double distance;
    private String duration;
}
