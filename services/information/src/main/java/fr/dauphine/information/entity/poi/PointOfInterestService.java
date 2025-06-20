package fr.dauphine.information.entity.poi;

import fr.dauphine.information.httpClient.DescriptionClient;
import fr.dauphine.information.dto.ActivityResponseDTO;
import fr.dauphine.information.dto.CreatePointOfInterestRequest;
import fr.dauphine.information.entity.activity.Activity;
import fr.dauphine.information.entity.city.City;
import fr.dauphine.information.entity.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointOfInterestService {

    private final CityService cityService;
    private final DescriptionClient descriptionClient;
    private final PointOfInterestRepository pointOfInterestRepository;

    public void createPOI(CreatePointOfInterestRequest request) {
        City city = cityService.findById(request.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("City with id ::" + request.getCityId() + ":: not found"));

        String mongoId = descriptionClient.createPoiDescription(request.getDescription());

        PointOfInterest poi = PointOfInterest.builder()
                .name(request.getName())
                .latitude(BigDecimal.valueOf(request.getLatitude()))
                .longitude(BigDecimal.valueOf(request.getLongitude()))
                .poiDescriptionId(mongoId)
                .city(city)
                .build();

        pointOfInterestRepository.save(poi);

        /* sql native option :
        pointOfInterestRepository.insertPOI(
                request.getName(),
                BigDecimal.valueOf(request.getLatitude()),
                BigDecimal.valueOf(request.getLongitude()),
                mongoId,
                request.getCityId()
        );
         */
    }

    public Optional<PointOfInterest> findById(Integer poiId) {
        return pointOfInterestRepository.findById(poiId);
        // option sql native
        // return cityRepository.findCityByIdNative(id);
    }

    public List<ActivityResponseDTO> getActivities(Integer poiId, boolean withDescription) {
        PointOfInterest poi = this.findById(poiId)
                .orElseThrow(() -> new IllegalArgumentException("PointOfInterest with id ::" + poiId + ":: not found"));
        List<Activity> activities = poi.getActivities();
        return activities.stream().map(activity -> {
            ActivityResponseDTO.ActivityResponseDTOBuilder dtoBuilder = ActivityResponseDTO.builder()
                    .id(activity.getId())
                    .name(activity.getName())
                    .price(activity.getPrice())
                    .duration(activity.getDuration())
                    .poiId(poi.getId());
            if (withDescription) {
                try {
                    Map<String, Object> description = descriptionClient.getActivityDescriptionById(activity.getActivityDescriptionId());
                    dtoBuilder.description(description);
                } catch (Exception e) {
                    dtoBuilder.description(null);
                }
            }

            return dtoBuilder.build();
        }).toList();
    }

    public List<PointOfInterest> getByName(String name) {
        return pointOfInterestRepository.findByNameLike(name);
    }

    public List<PointOfInterest> getByCityId(Integer cityId) {
        return pointOfInterestRepository.findByCityId(cityId);
    }

    public List<PointOfInterest> getByLatitudeRange(BigDecimal minLat, BigDecimal maxLat) {
        return pointOfInterestRepository.findByLatitudeBetween(minLat, maxLat);
    }

    public List<PointOfInterest> getByLongitudeRange(BigDecimal minLng, BigDecimal maxLng) {
        return pointOfInterestRepository.findByLongitudeBetween(minLng, maxLng);
    }

    public List<PointOfInterest> findAll() {
        return pointOfInterestRepository.findAll();
    }
}
