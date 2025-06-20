package fr.dauphine.information.entity.poi;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.dauphine.information.entity.activity.Activity;
import fr.dauphine.information.entity.city.City;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "point_of_interest")
public class PointOfInterest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private City city;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "description_mongo_id", nullable = false)
    private String poiDescriptionId;

    @JsonManagedReference
    @OneToMany(mappedBy = "pointOfInterest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

}
