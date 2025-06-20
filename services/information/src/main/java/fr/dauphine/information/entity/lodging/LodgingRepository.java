package fr.dauphine.information.entity.lodging;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LodgingRepository extends JpaRepository<Lodging, Integer> {

    @Modifying
    @Query(value = """
        INSERT INTO lodging (name, address, price_per_night, latitude, longitude, city_id)
        VALUES (:name, :address, :pricePerNight, :latitude, :longitude, :cityId)
        """, nativeQuery = true)
    void insertLodging(
            @Param("name") String name,
            @Param("address") String address,
            @Param("pricePerNight") BigDecimal pricePerNight,
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("cityId") Integer cityId
    );

    @Query(value = "SELECT * FROM lodging WHERE id = :id", nativeQuery = true)
    Optional<Lodging> findLodgingByIdNative(@Param("id") Integer id);

    @Query(value = "SELECT * FROM lodging WHERE city_id = :cityId", nativeQuery = true)
    List<Lodging> findLodgingsByCityIdNative(@Param("cityId") Integer cityId);

    @Query(value = "SELECT * FROM lodging WHERE name ILIKE %:name%", nativeQuery = true)
    List<Lodging> findByNameLike(@Param("name") String name);

    @Query(value = "SELECT * FROM lodging WHERE city_id = :cityId", nativeQuery = true)
    List<Lodging> findByCityId(@Param("cityId") Integer cityId);

    @Query(value = "SELECT * FROM lodging WHERE price_per_night <= :maxPrice", nativeQuery = true)
    List<Lodging> findByMaxPrice(@Param("maxPrice") BigDecimal maxPrice);

    @Query(value = "SELECT * FROM lodging WHERE latitude BETWEEN :minLat AND :maxLat", nativeQuery = true)
    List<Lodging> findByLatitudeBetween(@Param("minLat") BigDecimal minLat, @Param("maxLat") BigDecimal maxLat);

    @Query(value = "SELECT * FROM lodging WHERE longitude BETWEEN :minLng AND :maxLng", nativeQuery = true)
    List<Lodging> findByLongitudeBetween(@Param("minLng") BigDecimal minLng, @Param("maxLng") BigDecimal maxLng);


    /// jpa handled requests

    List<Lodging> findByCity_Id(Integer cityId);

}
