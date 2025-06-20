package fr.dauphine.roadsservice.service;


import fr.dauphine.roadsservice.dto.CreateCityDTO;
import fr.dauphine.roadsservice.entity.CityNode;
import fr.dauphine.roadsservice.repository.CityNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityNodeRepository cityRepo;

    public CityNode createCity(CreateCityDTO dto) {
        if (cityRepo.existsById(dto.getId())) {
            throw new IllegalArgumentException("City with ID " + dto.getId() + " already exists");
        }

        CityNode city = CityNode.builder()
                .id(dto.getId())
                .name(dto.getName())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .roads(List.of())
                .build();

        return cityRepo.save(city);
    }

//    public Optional<CityNode> findById(Long id) {
//        return cityRepo.findById(id);
//    }
//
//    public Optional<CityNode> findByName(String name) {
//        return cityRepo.findByName(name);
//    }
//
//    public List<CityNode> findAll() {
//        return cityRepo.findAll();
//    }
}
