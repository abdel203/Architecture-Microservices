package fr.dauphine.activities.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/activities")
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<String> createActivity(@RequestBody Map<String, Object> requestBody) {
        return ResponseEntity.ok().body(activityService.saveActivity(requestBody));
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok().body(activityService.getAllActivities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable String id) {
        return activityService.getActivityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/body")
    public ResponseEntity<Map<String, Object>> getActivityBodyById(@PathVariable String id) {
        return activityService.getActivityById(id)
                .map(activity -> ResponseEntity.ok().body(activity.getDescription()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/by-ids")
    public List<Activity> getActivitiesByIds(@RequestBody List<String> ids) {
        return activityService.getActivitiesByIds(ids);
    }

    @PostMapping("/filter")
    public List<Activity> filterActivities(@RequestBody Map<String, Object> filters) {
        return activityService.searchByRawFilter(filters);
    }
}