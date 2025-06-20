package fr.dauphine.information.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityMongoDTO {
    private String id;
    private Map<String, Object> description;
}
