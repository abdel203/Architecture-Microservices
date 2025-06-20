package fr.dauphine.roadsservice.controller;

import fr.dauphine.roadsservice.dto.CreateCityDTO;
import fr.dauphine.roadsservice.entity.CityNode;
import fr.dauphine.roadsservice.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roads/nodes")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<?> createCity(@RequestBody CreateCityDTO request) {
        CityNode city = cityService.createCity(request);
        return ResponseEntity.ok("city node created");
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CityNode> getCityById(@PathVariable Long id) {
//        return cityService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/by-name")
//    public ResponseEntity<CityNode> getCityByName(@RequestParam String name) {
//        return cityService.findByName(name)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CityNode>> getAllCities() {
//        return ResponseEntity.ok(cityService.findAll());
//    }
}
