package fr.dauphine.information.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResponseDTO {
    private Integer id;
    private String name;
    private Integer poiId;
    private BigDecimal price;
    private String duration;
    private Map<String, Object> description;
}
