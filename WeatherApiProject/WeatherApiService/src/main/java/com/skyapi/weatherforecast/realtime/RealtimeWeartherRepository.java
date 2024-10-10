package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.common.RealtimeWeather;

import org.springframework.data.repository.CrudRepository;

/** RealtimeWeartherRepository */
public interface RealtimeWeartherRepository extends CrudRepository<RealtimeWeather, String> {}
