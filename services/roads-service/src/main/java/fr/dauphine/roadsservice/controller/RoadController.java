package fr.dauphine.roadsservice.controller;

import fr.dauphine.roadsservice.dto.CreateRoadDTO;
import fr.dauphine.roadsservice.service.RoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roads")
@RequiredArgsConstructor
public class RoadController {

    private final RoadService roadService;

    @PostMapping("/routes")
    public ResponseEntity<String> createRoad(@RequestBody CreateRoadDTO request) {
        roadService.createRoad(request);
        return ResponseEntity.ok("Route created");
    }

    @GetMapping("/distance")
    public double getDistance(@RequestParam Integer fromId, @RequestParam Integer toId) {
        return roadService.getDistanceBetween(fromId, toId);
    }

    @GetMapping("/duration")
    public String getDuration(@RequestParam Integer fromId, @RequestParam Integer toId) {
        return roadService.getDurationBetween(fromId, toId);
    }

    @GetMapping("/intermediate-cities")
    public List<String> getIntermediateCities(@RequestParam Integer fromId, @RequestParam Integer toId) {
        return roadService.getIntermediateCities(fromId, toId);
    }
}

