package com.skyapi.weatherforecast.hourly;

import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.location.LocationNotFoundException;
import com.skyapi.weatherforecast.location.LocationRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/** HourlyWeatherService */
@Service
public class HourlyWeatherService {
    private HourlyWeatherRepository hourlyWeatherRepo;
    private LocationRepository locationRepo;

    public HourlyWeatherService(
            HourlyWeatherRepository hourlyWeatherRepo, LocationRepository locationRepo) {
        this.hourlyWeatherRepo = hourlyWeatherRepo;
        this.locationRepo = locationRepo;
    }

    public List<HourlyWeather> getByLocation(Location location, int currentHour)
            throws LocationNotFoundException {
        String countryCode = location.getCountryCode();
        String cityName = location.getCityName();

        Location locationInDB = locationRepo.findByCountryCodeAndCityName(countryCode, cityName);

        if (locationInDB == null) {
            throw new LocationNotFoundException(
                    "No location found with the given country code and city name");
        }

        return hourlyWeatherRepo.findByLocationCode(locationInDB.getCode(), currentHour);
    }
}
