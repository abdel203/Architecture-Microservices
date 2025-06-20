package fr.dauphine.information.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCityRequest {
    private String name;
    private double latitude;
    private double longitude;
}
