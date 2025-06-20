package fr.dauphine.roadsservice.service;

import fr.dauphine.roadsservice.dto.CreateRoadDTO;
import fr.dauphine.roadsservice.entity.CityNode;
import fr.dauphine.roadsservice.entity.Road;
import fr.dauphine.roadsservice.repository.CityNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadService {

    private final CityNodeRepository cityRepo;

    public void createRoad(CreateRoadDTO dto) {
        CityNode from = cityRepo.findById(dto.getFromCityId())
                .orElseThrow(() -> new RuntimeException("City with ID " + dto.getFromCityId() + " not found"));
        CityNode to = cityRepo.findById(dto.getToCityId())
                .orElseThrow(() -> new RuntimeException("City with ID " + dto.getToCityId() + " not found"));

        Road road = Road.builder()
                .destination(to)
                .distance(dto.getDistance())
                .duration(dto.getDuration())
                .build();

        if (from.getRoads() == null) {
            from.setRoads(new ArrayList<>());
        }

        from.getRoads().add(road);
        cityRepo.save(from); // Enregistre la relation ROAD_TO
    }

    public double getDistanceBetween(Integer fromId, Integer toId) {
        CityNode from = cityRepo.findById(fromId).orElseThrow(() -> new RuntimeException("City with ID " + fromId + " not found"));
        for (Road road : from.getRoads()) {
            if(road.getDestination().getId().equals(toId)) {
                return road.getDistance();
            }
        }
        throw new RuntimeException("City with ID " + toId + " not found");
    }

    public String getDurationBetween(Integer fromId, Integer toId) {
        CityNode from = cityRepo.findById(fromId).orElseThrow(() -> new RuntimeException("City with ID " + fromId + " not found"));
        for (Road road : from.getRoads()) {
            if(road.getDestination().getId().equals(toId)) {
                return road.getDuration();
            }
        }
        throw new RuntimeException("City with ID " + toId + " not found");
    }

    public List<String> getIntermediateCities(Integer fromId, Integer toId) {
        List<CityNode> paths = cityRepo.findAllPathsBetween(fromId, toId);
        return paths.stream().map(CityNode::getName).toList();
    }
}
