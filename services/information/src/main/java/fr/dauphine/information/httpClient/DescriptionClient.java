package fr.dauphine.information.httpClient;

import fr.dauphine.information.dto.ActivityMongoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "ACTIVITIES-SERVICE"
)
public interface DescriptionClient {

    @PostMapping("/api/v1/activities")
    String createActivityDescription(@RequestBody Map<String, Object> requestBody);

    @PostMapping("/api/v1/pois")
    String createPoiDescription(@RequestBody Map<String, Object> requestBody);

    @GetMapping("/api/v1/activities/{id}/body")
    Map<String, Object> getActivityDescriptionById(@PathVariable String id);

    @PostMapping("/api/v1/activities/filter")
    List<ActivityMongoDTO> searchActivities(@RequestBody Map<String, Object> filter);
}
