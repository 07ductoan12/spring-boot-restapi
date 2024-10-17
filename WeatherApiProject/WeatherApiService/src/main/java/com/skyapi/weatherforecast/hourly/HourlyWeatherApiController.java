package com.skyapi.weatherforecast.hourly;

import com.skyapi.weatherforecast.CommonUtility;
import com.skyapi.weatherforecast.GeoLocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.location.LocationNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** HourlyWeatherApiController */
@RestController
@RequestMapping("/v1/hourly")
public class HourlyWeatherApiController {
    private HourlyWeatherService hourlyWeatherService;
    private GeolocationService locationService;

    public HourlyWeatherApiController(
            HourlyWeatherService hourlyWeatherService, GeolocationService locationService) {
        this.hourlyWeatherService = hourlyWeatherService;
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<?> listHourlyForecastByIPAddress(HttpServletRequest request) {
        String ipAddress = CommonUtility.getIpAddress(request);

        int currentHour = Integer.parseInt(request.getHeader("X-Current-Hour"));

        Location locationFromIP;
        try {
            locationFromIP = locationService.getLocation(ipAddress);

            List<HourlyWeather> hourlyForecast =
                    hourlyWeatherService.getByLocation(locationFromIP, currentHour);

            if (hourlyForecast.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(hourlyForecast);

        } catch (GeoLocationException e) {
            return ResponseEntity.badRequest().build();
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
