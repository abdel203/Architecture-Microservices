package fr.dauphine.information.entity.lodging;

import fr.dauphine.information.dto.CreateLodgingRequest;
import fr.dauphine.information.entity.city.City;
import fr.dauphine.information.entity.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LodgingService {

    private final CityService cityService;
    private final LodgingRepository lodgingRepository;

    public void createLodging(CreateLodgingRequest request) {
        //option 1
        City city = cityService.findById(request.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("City not found"));

        Lodging lodging = Lodging.builder()
                .name(request.getName())
                .address(request.getAddress())
                .nightPrice(BigDecimal.valueOf(request.getPricePerNight()))
                .latitude(BigDecimal.valueOf(request.getLatitude()))
                .longitude(BigDecimal.valueOf(request.getLongitude()))
                .city(city)
                .build();

        lodgingRepository.save(lodging);

        /* Option 2 : méthode SQL native
        lodgingRepository.insertLodging(
                request.getName(),
                request.getAddress(),
                BigDecimal.valueOf(request.getPricePerNight()),
                BigDecimal.valueOf(request.getLatitude()),
                BigDecimal.valueOf(request.getLongitude()),
                request.getCityId()
        );
         */
    }

    public Optional<Lodging> findLodgingById(Integer id) {
        return lodgingRepository.findById(id);
        // sql native option :
        // return lodgingRepository.findCityByIdNative(id);
    }

    public List<Lodging> getLodgingsByCityId(Integer cityId) {
        return lodgingRepository.findByCity_Id(cityId);
        // sql native option
        // return lodgingRepository.findLodgingsByCityIdNative(cityId);
    }

    public List<Lodging> getByName(String name) {
        return lodgingRepository.findByNameLike(name);
    }

    public List<Lodging> getByCityId(Integer cityId) {
        return lodgingRepository.findByCityId(cityId);
    }

    public List<Lodging> getByMaxPrice(BigDecimal maxPrice) {
        return lodgingRepository.findByMaxPrice(maxPrice);
    }

    public List<Lodging> getByLatitudeRange(BigDecimal min, BigDecimal max) {
        return lodgingRepository.findByLatitudeBetween(min, max);
    }

    public List<Lodging> getByLongitudeRange(BigDecimal min, BigDecimal max) {
        return lodgingRepository.findByLongitudeBetween(min, max);
    }

}
