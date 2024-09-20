package com.toan.weatherforecast.realtime;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toan.weatherforecast.CommonUtility;
import com.toan.weatherforecast.GeolocationException;
import com.toan.weatherforecast.GeolocationService;
import com.toan.weatherforecast.common.Location;
import com.toan.weatherforecast.common.RealtimeWeather;
import com.toan.weatherforecast.location.LocationNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/realtime")
public class RealtimeWeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeWeatherRepository.class);
    private GeolocationService locationService;
    private RealtimeWeatherService realtimeWeatherService;
    private ModelMapper modelMapper;

    public RealtimeWeatherController(GeolocationService locationService,
            RealtimeWeatherService realtimeWeatherService, ModelMapper modelMapper) {
        this.locationService = locationService;
        this.realtimeWeatherService = realtimeWeatherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<?> getRealtimeWeatherByIPAddress(HttpServletRequest request) {
        String ipAddress = CommonUtility.getIPAddress(request);

        try {
            Location locationFromIP = locationService.getLocation(ipAddress);
            RealtimeWeather realtimeWeather = realtimeWeatherService.getByLocation(locationFromIP);

            RealtimeWeatherDTO realtimeWeatherDTO = modelMapper.map(realtimeWeather, RealtimeWeatherDTO.class);

            return ResponseEntity.ok(realtimeWeatherDTO);
        } catch (GeolocationException e) {
            LOGGER.error(e.getMessage(), e);

            return ResponseEntity.badRequest().build();
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

}
