package fr.dauphine.information.entity.city;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Modifying
    @Query(value = "INSERT INTO city(name, latitude, longitude) VALUES (:name, :latitude, :longitude)", nativeQuery = true)
    void insertCity(@Param("name") String name,
                    @Param("latitude") BigDecimal latitude,
                    @Param("longitude") BigDecimal longitude);

    @Query(value = "SELECT * FROM city WHERE id = :id", nativeQuery = true)
    Optional<City> findCityByIdNative(@Param("id") Integer id);

    @Query(value = """
        SELECT * FROM city
        WHERE id != :cityId
        AND haversine(
            (SELECT latitude FROM city WHERE id = :cityId),
            (SELECT longitude FROM city WHERE id = :cityId),
            latitude,
            longitude
        ) <= :distance
        """, nativeQuery = true)
    List<City> findNearbyCities(@Param("cityId") Integer cityId, @Param("distance") double distance);

    @Query(value = """
    SELECT haversine(
        c1.latitude, c1.longitude,
        c2.latitude, c2.longitude
    ) AS distance
    FROM city c1, city c2
    WHERE c1.id = :cityId1 AND c2.id = :cityId2
    """, nativeQuery = true)
    Double calculateDistanceBetweenCities(@Param("cityId1") Integer cityId1, @Param("cityId2") Integer cityId2);
}
