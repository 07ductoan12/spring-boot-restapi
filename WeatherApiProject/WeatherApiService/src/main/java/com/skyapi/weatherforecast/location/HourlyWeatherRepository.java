package com.skyapi.weatherforecast.location;

import org.springframework.data.repository.CrudRepository;
import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.HourlyWeatherId;

/**
 * HourlyWeatherRepository
 */
public interface HourlyWeatherRepository extends CrudRepository<HourlyWeather, HourlyWeatherId> {


}
