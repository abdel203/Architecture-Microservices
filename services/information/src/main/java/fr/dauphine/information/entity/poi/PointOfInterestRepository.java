package fr.dauphine.information.entity.poi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Integer> {

    @Modifying
    @Query(value = """
        INSERT INTO point_of_interest (name, latitude, longitude, description_mongo_id, city_id)
        VALUES (:name, :latitude, :longitude, :mongoId, :cityId)
        """, nativeQuery = true)
    void insertPOI(
            @Param("name") String name,
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("mongoId") String descriptionMongoId,
            @Param("cityId") Integer cityId
    );

    @Query(value = "SELECT * FROM point_of_interest WHERE id = :id", nativeQuery = true)
    Optional<PointOfInterest> findPointOfInterestByIdNative(@Param("id") Integer id);

    @Query(value = "SELECT * FROM point_of_interest WHERE name ILIKE %:name%", nativeQuery = true)
    List<PointOfInterest> findByNameLike(@Param("name") String name);

    @Query(value = "SELECT * FROM point_of_interest WHERE city_id = :cityId", nativeQuery = true)
    List<PointOfInterest> findByCityId(@Param("cityId") Integer cityId);

    @Query(value = "SELECT * FROM point_of_interest WHERE latitude >= :minLat AND latitude <= :maxLat", nativeQuery = true)
    List<PointOfInterest> findByLatitudeBetween(@Param("minLat") BigDecimal minLat, @Param("maxLat") BigDecimal maxLat);

    @Query(value = "SELECT * FROM point_of_interest WHERE longitude >= :minLng AND longitude <= :maxLng", nativeQuery = true)
    List<PointOfInterest> findByLongitudeBetween(@Param("minLng") BigDecimal minLng, @Param("maxLng") BigDecimal maxLng);


}
