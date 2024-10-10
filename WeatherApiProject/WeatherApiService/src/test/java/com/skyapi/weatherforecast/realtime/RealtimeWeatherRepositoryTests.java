package com.skyapi.weatherforecast.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import com.skyapi.weatherforecast.common.RealtimeWeather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

/** RealtimeWeatherRepository */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RealtimeWeatherRepositoryTests {

    @Autowired private RealtimeWeatherRepository repo;

    @Test
    public void testUpdate() {
        String locationCode = "NYC_USA";

        RealtimeWeather realtimeWeather = repo.findById(locationCode).get();

        realtimeWeather.setTemperature(-2);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setPrecipitation(42);
        realtimeWeather.setStatus("Snowy");
        realtimeWeather.setWindSpeed(12);
        realtimeWeather.setLastUpdated(new Date());

        RealtimeWeather updatedRealtimeWeather = repo.save(realtimeWeather);

        assertThat(updatedRealtimeWeather.getHumidity()).isEqualTo(32);
    }

    @Test
    public void testFindByCountryAndCityNotFound() {
        String countryCode = "JP";
        String cityName = "Tokyo";

        RealtimeWeather realtimeWeather = repo.findByCountryAndCity(countryCode, cityName);

        assertThat(realtimeWeather).isNull();
    }

    @Test
    public void testFindByCountryAndCityFound() {
        String countryCode = "US";
        String cityName = "New York City";

        RealtimeWeather realtimeWeather = repo.findByCountryAndCity(countryCode, cityName);

        assertThat(realtimeWeather).isNotNull();
        assertThat(realtimeWeather.getLocation().getCityName()).isEqualTo(cityName);
    }
}
