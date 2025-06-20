package fr.dauphine.information;

import fr.dauphine.information.dto.*;
import fr.dauphine.information.entity.activity.Activity;
import fr.dauphine.information.entity.activity.ActivityService;
import fr.dauphine.information.entity.city.City;
import fr.dauphine.information.entity.city.CityService;
import fr.dauphine.information.entity.lodging.Lodging;
import fr.dauphine.information.entity.lodging.LodgingService;
import fr.dauphine.information.entity.poi.PointOfInterest;
import fr.dauphine.information.entity.poi.PointOfInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/information")
public class InformationController {

    private final CityService cityService;
    private final LodgingService lodgingService;
    private final ActivityService activityService;
    private final PointOfInterestService pointOfInterestService;

    /// CREATE REQUESTS

    @PostMapping("/cities")
    public ResponseEntity<?> createCity(@RequestBody CreateCityRequest request) {
        cityService.createCity(request);
        return ResponseEntity.ok("City created");
    }

    @PostMapping("/citiesList")
    public ResponseEntity<?> createCities(@RequestBody List<CreateCityRequest> request) {
        request.forEach(cityService::createCity);
        return ResponseEntity.ok("Cities created");
    }

    @PostMapping("/lodgings")
    public ResponseEntity<?> createLodging(@RequestBody CreateLodgingRequest request) {
        lodgingService.createLodging(request);
        return ResponseEntity.ok("Lodging created");
    }

    @PostMapping("/lodgingsList")
    public ResponseEntity<?> createLodgings(@RequestBody List<CreateLodgingRequest> request) {
        request.forEach(lodgingService::createLodging);
        return ResponseEntity.ok("Lodgings created");
    }

    @PostMapping("/pois")
    public ResponseEntity<?> createPOI(@RequestBody CreatePointOfInterestRequest request) {
        pointOfInterestService.createPOI(request);
        return ResponseEntity.ok("POI created");
    }

    @PostMapping("/poiList")
    public ResponseEntity<?> createPOIs(@RequestBody List<CreatePointOfInterestRequest> request) {
        request.forEach(pointOfInterestService::createPOI);
        return ResponseEntity.ok("POIs created");
    }

    @PostMapping("/activities")
    public ResponseEntity<?> createActivity(@RequestBody CreateActivityRequest request) {
        activityService.createActivity(request);
        return ResponseEntity.ok("Activity created");
    }

    @PostMapping("/activitiesList")
    public ResponseEntity<?> createActivities(@RequestBody List<CreateActivityRequest> request) {
        request.forEach(activityService::createActivity);
        return ResponseEntity.ok("Activities created");
    }

    /// FIND ALL REQUESTS
    @GetMapping("/cities")
    public ResponseEntity<List<City>> findAllCities() {
        return ResponseEntity.ok().body(cityService.findAll());
    }

    @GetMapping("/pois")
    public ResponseEntity<List<PointOfInterest>> findAllPOIs() {
        return ResponseEntity.ok().body(pointOfInterestService.findAll());
    }

    /// FILTER REQUESTS

    //---------------------------------------------------------------------------------------------------------------
    //                                ACTIVITIES FILTERS
    //---------------------------------------------------------------------------------------------------------------

    // 2. Quelles sont les activités associées à un point d’intérêt donné ?
    @GetMapping("/pois/{poiId}/activities")
    public ResponseEntity<List<ActivityResponseDTO>> getActivitiesByPoiId(
            @PathVariable int poiId,
            @RequestParam(defaultValue = "false") boolean withDescription
    ) {
        List<ActivityResponseDTO> activities = pointOfInterestService.getActivities(poiId, withDescription);
        return ResponseEntity.ok(activities);
    }

    // 5. Quelles activités se font entre avril et juin ?
    /* with this filter :
    {
        "description.practicePeriod": {
            "$regex": "(?i).*avril.*juin.*"
        }
    }
     */
    @PostMapping("/activities/filter")
    public ResponseEntity<List<ActivityResponseDTO>> getFilteredActivities(
            @RequestBody Map<String, Object> filter,
            @RequestParam(defaultValue = "false") boolean withDescription
    ) {
        List<ActivityResponseDTO> activities = activityService.searchActivitiesByDescriptionFilter(filter, withDescription);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/activities/by-price")
    public ResponseEntity<List<Activity>> getActivitiesByPrice(@RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(activityService.getActivitiesByMaxPrice(maxPrice));
    }

    @GetMapping("/activities/by-duration")
    public ResponseEntity<List<Activity>> getActivitiesByMinDuration(@RequestParam Duration minDuration) {
        return ResponseEntity.ok(activityService.getActivitiesByMinDuration(minDuration));
    }

    @GetMapping("/activities/by-name")
    public ResponseEntity<List<Activity>> getActivitiesByName(@RequestParam String name) {
        return ResponseEntity.ok(activityService.getActivitiesByName(name));
    }

    //---------------------------------------------------------------------------------------------------------------
    //                                POINTS OF INTEREST FILTERS
    //---------------------------------------------------------------------------------------------------------------

    @GetMapping("/pois/by-name")
    public ResponseEntity<List<PointOfInterest>> getPOIsByName(@RequestParam String name) {
        return ResponseEntity.ok(pointOfInterestService.getByName(name));
    }

    @GetMapping("/pois/by-city")
    public ResponseEntity<List<PointOfInterest>> getPOIsByCity(@RequestParam Integer cityId) {
        return ResponseEntity.ok(pointOfInterestService.getByCityId(cityId));
    }

    @GetMapping("/pois/by-latitude")
    public ResponseEntity<List<PointOfInterest>> getPOIsByLatitudeRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(pointOfInterestService.getByLatitudeRange(min, max));
    }

    @GetMapping("/pois/by-longitude")
    public ResponseEntity<List<PointOfInterest>> getPOIsByLongitudeRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(pointOfInterestService.getByLongitudeRange(min, max));
    }

    //---------------------------------------------------------------------------------------------------------------
    //                                LODGING FILTERS
    //---------------------------------------------------------------------------------------------------------------

    @GetMapping("/lodgings/by-name")
    public ResponseEntity<List<Lodging>> getLodgingsByName(@RequestParam String name) {
        return ResponseEntity.ok(lodgingService.getByName(name));
    }

    @GetMapping("/lodgings/by-city")
    public ResponseEntity<List<Lodging>> getLodgingsByCity(@RequestParam Integer cityId) {
        return ResponseEntity.ok(lodgingService.getByCityId(cityId));
    }

    @GetMapping("/lodgings/by-price")
    public ResponseEntity<List<Lodging>> getLodgingsByPrice(@RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(lodgingService.getByMaxPrice(maxPrice));
    }

    @GetMapping("/lodgings/by-latitude")
    public ResponseEntity<List<Lodging>> getLodgingsByLatitude(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(lodgingService.getByLatitudeRange(min, max));
    }

    @GetMapping("/lodgings/by-longitude")
    public ResponseEntity<List<Lodging>> getLodgingsByLongitude(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(lodgingService.getByLongitudeRange(min, max));
    }

    // 3. Quels sont les hébergements d’une ville ?
    @GetMapping("/cities/{cityId}/lodgings")
    public ResponseEntity<List<Lodging>> getLodgingsByCityId(@PathVariable Integer cityId) {
        List<Lodging> lodgings = lodgingService.getLodgingsByCityId(cityId);
        return ResponseEntity.ok(lodgings);
    }

    //---------------------------------------------------------------------------------------------------------------
    //                                CITIES FILTERS
    //---------------------------------------------------------------------------------------------------------------

    // 1. Quels sont les villes situées à moins de 10km d’une ville donnée ?
    @GetMapping("/cities/{cityId}/nearby")
    public ResponseEntity<List<City>> getNearbyCities(
            @PathVariable Integer cityId,
            @RequestParam(defaultValue = "10") double distanceKm
    ) {
        List<City> nearbyCities = cityService.findNearbyCities(cityId, distanceKm);
        return ResponseEntity.ok(nearbyCities);
    }

    ///  ROADS
    // create road between two cities

    @PostMapping("/roads")
    public ResponseEntity<?> createRoad(@RequestBody CreateRoadDTO createRoadDTO) {
        cityService.createRoad(createRoadDTO);
        return ResponseEntity.ok("road created");
    }

    @PostMapping("/roadsList")
    public ResponseEntity<?> createRoads(@RequestBody List<CreateRoadDTO> createRoadDTOs) {
        createRoadDTOs.forEach(cityService::createRoad);
        return ResponseEntity.ok("roads created");
    }

    //6. Etant données une ville de départ et une ville d’arrivée, quels sont les différentes villes possibles à
    //visiter entre les 2 ?
    @GetMapping("roads/intermediate-cities")
    public List<String>  getIntermediateCities(@RequestParam Integer fromId, @RequestParam Integer toId) {
        return cityService.getIntermediateCities(fromId, toId);
    }
}
