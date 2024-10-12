package com.skyapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

import com.skyapi.weatherforecast.common.HourlyWeather;
import com.skyapi.weatherforecast.common.HourlyWeatherId;
import com.skyapi.weatherforecast.common.Location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

/** HourlyRepositoryTests */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class HourlyRepositoryTests {
    @Autowired
    private HourlyWeatherRepository repo;

    @Test
    public void testAdd() {
        String locationCode = "DELHI_IN";
        int hourOfDay = 12;

        Location location = new Location().code(locationCode);

        HourlyWeather forecast = new HourlyWeather().location(location).hourOfDay(hourOfDay)
                .temperature(13).precipitation(70).status("Clody");

        HourlyWeather updatedForecast = repo.save(forecast);

        assertThat(updatedForecast.getId().getLocation().getCode()).isEqualTo(locationCode);
        assertThat(updatedForecast.getId().getHourOfDay()).isEqualTo(hourOfDay);
    }

    @Test
    public void testDelete() {
        Location location = new Location().code("DELHI_IN");
        HourlyWeatherId id = new HourlyWeatherId(10, location);

        repo.deleteById(id);

        Optional<HourlyWeather> result = repo.findById(id);
        assertThat(result).isNotPresent();
    }
}
