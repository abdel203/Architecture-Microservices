package fr.dauphine.information.entity.city;

import fr.dauphine.information.dto.*;
import fr.dauphine.information.httpClient.RoadsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final RoadsClient roadsClient;

    public void createCity(CreateCityRequest request) {
        //option 1
        City cityObject = City.builder()
                .name(request.getName())
                .latitude(BigDecimal.valueOf(request.getLatitude()))
                .longitude(BigDecimal.valueOf(request.getLongitude()))
                .build();
        City city = cityRepository.save(cityObject);

        // create city node in roads service
        roadsClient.createActivityDescription(
                CreateCityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .build());

        /* Option 2 sql native :
        cityRepository.insertCity(
            request.getName(),
            BigDecimal.valueOf(request.getLatitude()),
            BigDecimal.valueOf(request.getLongitude())
        );
         */
    }

    public Optional<City> findById(Integer cityId) {
        return cityRepository.findById(cityId);
        // option sql native
        // return cityRepository.findCityByIdNative(id);
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public List<City> findNearbyCities(Integer cityId, double distanceKm) {
        return cityRepository.findNearbyCities(cityId, distanceKm);
    }

    public void createRoad(CreateRoadDTO createRoadDTO) {
        if (!cityRepository.existsById(createRoadDTO.getToCityId())) {
            throw new IllegalArgumentException("The city id " + createRoadDTO.getToCityId() + " does not exist");
        }
        if(!cityRepository.existsById(createRoadDTO.getFromCityId())) {
            throw new IllegalArgumentException("The city id " + createRoadDTO.getFromCityId() + " does not exist");
        }
        Double distanceBetweenCities = cityRepository.calculateDistanceBetweenCities(createRoadDTO.getFromCityId(), createRoadDTO.getToCityId());
        String duration = timeUtil(distanceBetweenCities).toString();
        roadsClient.createRoad(
                CreateRoadNeoDTO.builder()
                        .fromCityId(createRoadDTO.getFromCityId())
                        .toCityId(createRoadDTO.getToCityId())
                        .distance(distanceBetweenCities)
                        .duration(duration)
                        .build()
        );
    }

    public static Duration timeUtil(Double distanceKm) {
        double hours = distanceKm / 80.0; // 80 km = 1h
        long seconds = (long) (hours * 3600); // Convertir en secondes
        return Duration.ofSeconds(seconds);
    }

    public List<String>  getIntermediateCities(Integer fromId, Integer toId) {
        return roadsClient.getIntermediateCities(fromId, toId);
    }
}
