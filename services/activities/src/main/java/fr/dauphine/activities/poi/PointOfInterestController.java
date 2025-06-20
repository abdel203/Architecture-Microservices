package fr.dauphine.activities.poi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pois")
public class PointOfInterestController {

    private final PointOfInterestService pointOfInterestService;

    @PostMapping
    public ResponseEntity<String> createActivity(@RequestBody Map<String, Object> requestBody) {
        return ResponseEntity.ok().body(pointOfInterestService.savePoi(requestBody));
    }

    @GetMapping
    public ResponseEntity<List<PointOfInterest>> getAllActivities() {
        return ResponseEntity.ok().body(pointOfInterestService.getPois());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointOfInterest> getActivityById(@PathVariable String id) {
        return pointOfInterestService.getPoiById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/by-ids")
    public List<PointOfInterest> getActivitiesByIds(@RequestBody List<String> ids) {
        return pointOfInterestService.getPoisByIds(ids);
    }

    @PostMapping("/filter")
    public List<PointOfInterest> filterActivities(@RequestBody Map<String, Object> filters) {
        return pointOfInterestService.searchByRawFilter(filters);
    }
}
