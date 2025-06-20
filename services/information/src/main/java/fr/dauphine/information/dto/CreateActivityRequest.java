package fr.dauphine.information.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateActivityRequest {
    private String name;
    private BigDecimal price;
    private String duration;
    private Integer poiId;
    private Map<String, Object> description;
}
