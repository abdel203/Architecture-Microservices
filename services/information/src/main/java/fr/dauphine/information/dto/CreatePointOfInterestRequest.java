package fr.dauphine.information.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePointOfInterestRequest {
    private String name;
    private Integer cityId;
    private double latitude;
    private double longitude;
    private Map<String, Object> description;
}
