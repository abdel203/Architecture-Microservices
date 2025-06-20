package fr.dauphine.information.entity.activity;

import fr.dauphine.information.entity.poi.PointOfInterest;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @JoinColumn(name = "poi_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private PointOfInterest pointOfInterest;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "description_mongo_id", nullable = false)
    private String activityDescriptionId;

}
