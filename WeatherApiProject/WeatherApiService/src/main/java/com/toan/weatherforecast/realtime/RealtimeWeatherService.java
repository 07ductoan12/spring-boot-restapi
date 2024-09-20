package com.toan.weatherforecast.realtime;

import org.springframework.stereotype.Service;

import com.toan.weatherforecast.common.Location;
import com.toan.weatherforecast.common.RealtimeWeather;
import com.toan.weatherforecast.location.LocationNotFoundException;

@Service
public class RealtimeWeatherService {
    private RealtimeWeatherRepository realtimeWeatherRepo;

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
}
