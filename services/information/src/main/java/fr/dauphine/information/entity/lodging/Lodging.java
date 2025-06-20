package fr.dauphine.information.entity.lodging;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.dauphine.information.entity.city.City;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "lodging")
public class Lodging {

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

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "price_per_night", nullable = false)
    private BigDecimal nightPrice;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

}
