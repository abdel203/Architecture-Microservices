package fr.dauphine.information.entity.activity;

import fr.dauphine.information.httpClient.DescriptionClient;
import fr.dauphine.information.dto.ActivityMongoDTO;
import fr.dauphine.information.dto.ActivityResponseDTO;
import fr.dauphine.information.dto.CreateActivityRequest;
import fr.dauphine.information.entity.poi.PointOfInterest;
import fr.dauphine.information.entity.poi.PointOfInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final DescriptionClient descriptionClient;
    private final ActivityRepository activityRepository;
    private final PointOfInterestService pointOfInterestService;

    public void createActivity(CreateActivityRequest request) {
        PointOfInterest poi = pointOfInterestService.findById(request.getPoiId()).orElseThrow(
                () -> new IllegalArgumentException("poiId " + request.getPoiId() + " not found")
        );
        String mongoId = descriptionClient.createActivityDescription(request.getDescription());
        Activity activity = Activity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .duration(request.getDuration())
                .pointOfInterest(poi)
                .activityDescriptionId(mongoId)
                .build();
        activityRepository.save(activity);

        /* SQL native option
        activityRepository.insertActivity(
                request.getPoiId(),
                request.getName(),
                request.getPrice(),
                request.getDuration(),
                mongoId
        );
         */
    }

    public Optional<Activity> findById(Integer id) {
        return activityRepository.findById(id);
        // sql native option :
        // activityRepository.findByIdNative(id);
    }

    public List<Activity> findByDescriptionIds(List<String> ids) {
        return activityRepository.findAllByActivityDescriptionIdIn(ids);
        // sql native option :
        // activityRepository.findAllByActivityDescriptionIdInNative(id);
    }

    public List<ActivityResponseDTO> searchActivitiesByDescriptionFilter(Map<String, Object> filter, boolean withDescription) {
        List<ActivityMongoDTO> activitiesDescription = descriptionClient.searchActivities(filter);
        List<String> descriptionIds = activitiesDescription.stream().map(ActivityMongoDTO::getId).toList();
        List<Activity> activities = this.findByDescriptionIds(descriptionIds);

        return activities.stream().map(activity -> {
            ActivityResponseDTO.ActivityResponseDTOBuilder dtoBuilder = ActivityResponseDTO.builder()
                    .id(activity.getId())
                    .name(activity.getName())
                    .price(activity.getPrice())
                    .duration(activity.getDuration())
                    .poiId(null);
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

    public List<Activity> getActivitiesByMaxPrice(BigDecimal maxPrice) {
        return activityRepository.findByMaxPrice(maxPrice);
    }

    public List<Activity> getActivitiesByMinDuration(Duration minDuration) {
        return activityRepository.findByMinDuration(minDuration);
    }

    public List<Activity> getActivitiesByName(String namePart) {
        return activityRepository.findByNameLike(namePart);
    }
}
