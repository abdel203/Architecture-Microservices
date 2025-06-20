package fr.dauphine.trip.service;

import fr.dauphine.trip.dto.*;
import fr.dauphine.trip.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripMapper {

    public Trip toTrip(TripCreationRequest request) {
        String reference = generateReference(request.getStartDate().toString());

        // Mapping des StepDTO vers Step
        List<Step> steps = request.getSteps()
                .stream()
                .map(this::mapStep)
                .collect(Collectors.toList());

        return Trip.builder()
                .reference(reference)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .steps(steps)
                .build();
    }

    private Step mapStep(StepDTO dto) {
        return Step.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .city(dto.getCity())
                .activities(dto.getActivities().stream()
                        .map(this::mapActivity)
                        .collect(Collectors.toList()))
                .lodging(mapLodging(dto.getLodging()))
                .nextStep(mapNextStep(dto.getNextStepDTO()))
                .build();
    }

    private Activity mapActivity(ActivityDTO dto) {
        return Activity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .poiName(dto.getPoiName())
                .date(dto.getDate())
                .build();
    }

    private Lodging mapLodging(LodgingDTO dto) {
        return Lodging.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .address(dto.getAddress())
                .build();
    }

    private NextStep mapNextStep(NextStepDTO dto) {
        return NextStep.builder()
                .city(dto.getCity())
                .distance(dto.getDistance())
                .travelTime(dto.getTravelTime())
                .build();
    }

    //-----------------------------------------------------------------------

    public TripSummary toTripSummary(Trip trip) {
        // 1) on reconstruit le DTO TripCreationRequest
        TripCreationRequest dto = TripCreationRequest.builder()
                .steps(trip.getSteps().stream()
                        .map(this::mapStepDto)
                        .collect(Collectors.toList()))
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .build();

        // 2) on crée le TripSummary avec la référence et le DTO
        return new TripSummary(trip.getReference(), dto);
    }

    private StepDTO mapStepDto(Step step) {
        return StepDTO.builder()
                .city(step.getCity())
                .startDate(step.getStartDate())
                .endDate(step.getEndDate())
                .activities(
                        step.getActivities().stream()
                                .map(this::mapActivityDto)
                                .collect(Collectors.toList()))
                .lodging(mapLodgingDto(step.getLodging()))
                .nextStep(mapNextStepDto(step.getNextStep()))
                .build();
    }

    private ActivityDTO mapActivityDto(Activity a) {
        return ActivityDTO.builder()
                .name(a.getName())
                .price(a.getPrice())
                .poiName(a.getPoiName())
                .date(a.getDate())
                .build();
    }

    private LodgingDTO mapLodgingDto(Lodging l) {
        return LodgingDTO.builder()
                .name(l.getName())
                .price(l.getPrice())
                .address(l.getAddress())
                .build();
    }

    private NextStepDTO mapNextStepDto(NextStep ns) {
        return NextStepDTO.builder()
                .city(ns.getCity())
                .distance(ns.getDistance())
                .travelTime(ns.getTravelTime())
                .build();
    }

    //-----------------------------------------------------------------------

    private String generateReference(String s) {
        return "TRIP-" + LocalDateTime.now() + "-" + s;
    }
}
