package fr.dauphine.information.httpClient;

import fr.dauphine.information.dto.CreateCityDTO;
import fr.dauphine.information.dto.CreateRoadNeoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "ROADS-SERVICE"
)
public interface RoadsClient {

    @PostMapping("/api/v1/roads/nodes")
    String createActivityDescription(@RequestBody CreateCityDTO requestBody);

    @PostMapping("/api/v1/roads/routes")
    String createRoad(@RequestBody CreateRoadNeoDTO requestBody);

    @GetMapping("/api/v1/roads/intermediate-cities")
    List<String> getIntermediateCities(@RequestParam Integer fromId, @RequestParam Integer toId);
}
