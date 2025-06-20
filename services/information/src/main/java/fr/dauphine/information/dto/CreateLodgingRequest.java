package fr.dauphine.information.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLodgingRequest {
    private String name;
    private String address;
    private double pricePerNight;
    private double latitude;
    private double longitude;
    private Integer cityId;  // FK
}
