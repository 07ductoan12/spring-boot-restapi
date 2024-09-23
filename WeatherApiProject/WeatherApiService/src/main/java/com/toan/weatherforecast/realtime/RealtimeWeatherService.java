package com.toan.weatherforecast.realtime;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.toan.weatherforecast.common.Location;
import com.toan.weatherforecast.common.RealtimeWeather;
import com.toan.weatherforecast.location.LocationNotFoundException;
import com.toan.weatherforecast.location.LocationRepository;

@Service
public class RealtimeWeatherService {
    private RealtimeWeatherRepository realtimeWeatherRepo;
    private LocationRepository locationRepo;

    public RealtimeWeatherService(RealtimeWeatherRepository realtimeWeatherRepo) {
        this.realtimeWeatherRepo = realtimeWeatherRepo;
    }

    public RealtimeWeather getByLocation(Location location) throws LocationNotFoundException {
        String countryCode = location.getCountryCode();
        String cityName = location.getCityName();

        RealtimeWeather realtimeWeather = realtimeWeatherRepo.findByCountryCodeAndCity(countryCode, cityName);

        if (realtimeWeather == null) {
            throw new LocationNotFoundException("No location found with the given country code and city name");
        }

        return realtimeWeather;
    }

    public RealtimeWeather getByLocationCode(String locationCode) throws LocationNotFoundException {
        RealtimeWeather realtimeWeather = realtimeWeatherRepo.findByLocationCode(locationCode);

        if (realtimeWeather == null) {
            throw new LocationNotFoundException("No location found with the given code: " + locationCode);
        }

        return realtimeWeather;
    }

    public RealtimeWeather update(String locationCode, RealtimeWeather realtimeWeather)
            throws LocationNotFoundException {
        Location location = locationRepo.findByCode(locationCode);

        if (location == null) {
            throw new LocationNotFoundException("No location found with the given code: " + locationCode);
        }

        realtimeWeather.setLocation(location);
        realtimeWeather.setLastUpdated(new Date(System.currentTimeMillis()));

        return realtimeWeatherRepo.save(realtimeWeather);
    }
}
