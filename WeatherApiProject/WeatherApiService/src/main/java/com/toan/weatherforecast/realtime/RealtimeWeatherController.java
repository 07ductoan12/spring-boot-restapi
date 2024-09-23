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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

            return ResponseEntity.ok(entityReatimeWeather2DTO(realtimeWeather));
        } catch (GeolocationException e) {
            LOGGER.error(e.getMessage(), e);

            return ResponseEntity.badRequest().build();
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{locationCode}")
    public ResponseEntity<?> getRealtimeWeatherByLocationCode(@PathVariable("locationCode") String locationCode) {
        try {
            RealtimeWeather realtimeWeather = realtimeWeatherService.getByLocationCode(locationCode);

            return ResponseEntity.ok(entityReatimeWeather2DTO(realtimeWeather));
        } catch (LocationNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{locationCode}")
    public ResponseEntity<?> updateRealtimeWearther(@PathVariable("locationCode") String locationCode,
            @RequestBody RealtimeWeather realtimeWeatherInRequest) {

        try {
            RealtimeWeather updatedRealtimeWeather = realtimeWeatherService.update(locationCode,
                    realtimeWeatherInRequest);
            return ResponseEntity.ok(entityReatimeWeather2DTO(updatedRealtimeWeather));
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    private RealtimeWeatherDTO entityReatimeWeather2DTO(RealtimeWeather realtimeWeather) {
        return modelMapper.map(realtimeWeather, RealtimeWeatherDTO.class);
    }

}
