package fr.dauphine.trip.controller;

import fr.dauphine.trip.dto.TripCreationRequest;
import fr.dauphine.trip.dto.TripSummary;
import fr.dauphine.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trip")
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<String> createTip(
            @RequestBody TripCreationRequest tripCreationRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripService.createTrip(tripCreationRequest));
    }

    @GetMapping("/{trip-reference}")
    public ResponseEntity<TripSummary> getTripByReference(
            @PathVariable(value = "trip-reference") String tripReference
    ) {
        return ResponseEntity.ok(tripService.getTripByReference(tripReference));
    }

    //todo : update trip
}
