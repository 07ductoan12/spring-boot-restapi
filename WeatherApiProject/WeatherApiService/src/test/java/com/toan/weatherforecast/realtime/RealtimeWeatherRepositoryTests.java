package com.toan.weatherforecast.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.toan.weatherforecast.common.RealtimeWeather;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RealtimeWeatherRepositoryTests {
    @Autowired
    private RealtimeWeatherRepositor repo;

    @Test
    public void testUpdate() {

        String locationCode = "NYC_USA";

        RealtimeWeather realtimeWeather = repo.findById(locationCode).get();

        realtimeWeather.setTemperature(-2);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setPrecipation(42);
        realtimeWeather.setStatus("Snowy");
        realtimeWeather.setWindSpeed(12);
        realtimeWeather.setLastUpdated(new Date(System.currentTimeMillis()));

        RealtimeWeather updatedRealtimeWeather = repo.save(realtimeWeather);

        assertThat(updatedRealtimeWeather.getHumidity()).isEqualTo(32);
    }

    @Test
    public void testFindByCountryCodeAndCityNotFound() {
        String countryCode = "JP";
        String cityName = "Tokyo";

        RealtimeWeather realtimeWeather = repo.findByCountryCodeAndCity(countryCode, cityName);

        assertThat(realtimeWeather).isNull();
    }
}
