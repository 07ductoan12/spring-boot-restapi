package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.RealtimeWeather;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/** RealtimeWeartherRepository */
public interface RealtimeWeatherRepository extends CrudRepository<RealtimeWeather, String> {

    @Query(
            "SELECT r FROM RealtimeWeather r WHERE r.location.countryCode = ?1 AND"
                + " r.location.cityName = ?2")
    public RealtimeWeather findByCountryAndCity(String countryCode, String cityName);
}
