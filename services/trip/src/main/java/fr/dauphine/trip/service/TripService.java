package fr.dauphine.trip.service;

import fr.dauphine.trip.dto.ActivityDTO;
import fr.dauphine.trip.dto.StepDTO;
import fr.dauphine.trip.dto.TripCreationRequest;
import fr.dauphine.trip.dto.TripSummary;
import fr.dauphine.trip.repository.TripRepository;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public String createTrip(TripCreationRequest tripCreationRequest) {
        StringBuilder exceptionAccumulator = new StringBuilder();
        if(validateSchema(exceptionAccumulator ,tripCreationRequest)) {
            throw new IllegalArgumentException(exceptionAccumulator.toString());
        }
        return tripRepository
                .save(tripMapper.toTrip(tripCreationRequest))
                .getReference();
    }

    public TripSummary getTripByReference(String reference) {
        return tripRepository.findByReference(reference)
                .map(tripMapper::toTripSummary)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with reference:: " + reference));
    }

    private boolean validateSchema(StringBuilder accumulator, TripCreationRequest trip) {
        if (trip.getEndDate().isBefore(trip.getStartDate())) {
            accumulator.append("trip start date should be set before trip end date");
            return false;
        }
        for (StepDTO step : trip.getSteps()) {
            if (step.getEndDate().isBefore(step.getStartDate())) {
                accumulator.append("step end date should be set before step start date");
                return false;
            }
            if(step.getStartDate().isAfter(trip.getEndDate())) {
                accumulator.append("step start date should be before trip end date");
                return false;
            }
            if(step.getEndDate().isAfter(trip.getEndDate())) {
                accumulator.append("step end date should be before trip end date");
            }
            if(step.getStartDate().isBefore(trip.getStartDate())) {
                accumulator.append("step start date should be after trip start date");
                return false;
            }
            if(step.getEndDate().isBefore(trip.getEndDate())) {
                accumulator.append("step end date should be after trip end date");
                return false;
            }
            for (ActivityDTO activity : step.getActivities()) {
                if (activity.getDate().isBefore(step.getStartDate())) {
                    accumulator.append("activity date should be after activity start date");
                }
                if(activity.getDate().isAfter(trip.getEndDate())) {
                    accumulator.append("activity date should be before activity end date");
                }
            }
            if (step.getNextStepDTO().getCity().equals(step.getCity())) {
                accumulator.append("next step city should be different then actual city");
            }
            boolean nextStepExists = trip.getSteps().stream()
                    .map(StepDTO::getCity)
                    .filter(city -> !city.equals(step.getCity()))
                    .anyMatch(city -> city.equals(step.getNextStepDTO().getCity()));
            if (!nextStepExists) {
                accumulator.append("next step city should exists in the trip");
                return false;
            }
        }
        return true;
    }
}
