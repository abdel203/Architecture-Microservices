package fr.dauphine.trip.repository;

import fr.dauphine.trip.entity.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {

    Optional<Trip> findByReference(String reference);

}
