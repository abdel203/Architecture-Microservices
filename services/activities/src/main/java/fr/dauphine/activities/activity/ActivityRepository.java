package fr.dauphine.activities.activity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    // db.activities.find(param); with *param* being what inside the query annotation

    @Override
    @Query("{ '_id': ?0 }")
    Optional<Activity> findById(String id);

    @Override
    @Query("{}")
    List<Activity> findAll();

    @Query("{ '_id': { '$in': ?0 } }")
    List<Activity> findAllById(List<String> ids);

}

