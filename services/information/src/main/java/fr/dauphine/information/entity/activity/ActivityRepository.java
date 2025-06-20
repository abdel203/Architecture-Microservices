package fr.dauphine.information.entity.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Modifying
    @Query(value = """
        INSERT INTO activity (poi_id, name, price, duration, description_mongo_id)
        VALUES (:poiId, :name, :price, :duration, :mongoId)
        """, nativeQuery = true)
    void insertActivity(
            @Param("poiId") Integer poiId,
            @Param("name") String name,
            @Param("price") BigDecimal price,
            @Param("duration") Duration duration,
            @Param("mongoId") String descriptionMongoId
    );

    @Query(value = "SELECT * FROM activity WHERE id = :id", nativeQuery = true)
    Optional<Activity> findByIdNative(@Param("id") Integer id);

    @Query(value = "SELECT * FROM activity WHERE description_mongo_id IN (:ids)", nativeQuery = true)
    List<Activity> findAllByActivityDescriptionIdInNative(@Param("ids") List<String> ids);

    @Query(value = "SELECT * FROM activity WHERE price <= :maxPrice", nativeQuery = true)
    List<Activity> findByMaxPrice(@Param("maxPrice") BigDecimal maxPrice);

    @Query(value = "SELECT * FROM activity WHERE duration >= :minDuration", nativeQuery = true)
    List<Activity> findByMinDuration(@Param("minDuration") Duration minDuration);

    @Query(value = "SELECT * FROM activity WHERE poi_id = :poiId", nativeQuery = true)
    List<Activity> findByPoiId(@Param("poiId") Integer poiId);

    @Query(value = "SELECT * FROM activity WHERE name ILIKE %:name%", nativeQuery = true)
    List<Activity> findByNameLike(@Param("name") String name);

    //jpa query generation

    List<Activity> findAllByActivityDescriptionIdIn(List<String> ids);
}
