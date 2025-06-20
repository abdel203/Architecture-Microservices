package fr.dauphine.activities.poi;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "pois")
public class PointOfInterest {

    @Id
    private String id;

    @Field("description")
    private Map<String, Object> description;

}
