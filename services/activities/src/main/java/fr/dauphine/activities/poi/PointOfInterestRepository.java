package fr.dauphine.activities.poi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointOfInterestRepository extends MongoRepository<PointOfInterest, String>  {

    // db.pois.find(param); with *param* being what inside the query annotation

    @Override
    @Query("{ '_id': ?0 }")
    Optional<PointOfInterest> findById(String id);

    @Override
    @Query("{}")
    List<PointOfInterest> findAll();

    @Query("{ '_id': { '$in': ?0 } }")
    List<PointOfInterest> findAllById(List<String> ids);

}
