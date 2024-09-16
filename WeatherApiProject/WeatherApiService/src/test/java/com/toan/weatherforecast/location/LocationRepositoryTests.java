package com.toan.weatherforecast.location;

import com.toan.weatherforecast.common.Location;
import com.toan.weatherforecast.common.RealtimeWeather;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTests {
    @Autowired
    private LocationRepository repository;

    @Test
    public void testAddSuccess() {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of American");
        location.setEnabled(true);

        Location savedLocation = repository.save(location);
        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getCode()).isEqualTo("NYC_USA");
    }

    @Test
    public void testListSuccess() {

        List<Location> locations = repository.findUntrashed();

        assertThat(locations).isNotEmpty();

        System.out.println(locations);
        locations.forEach(System.out::println);
    }

    @Test
    public void testGetNotFound() {
        String code = "ABCD";
        Location location = repository.findByCode(code);

        assertThat(location).isNull();

    }

    @Test
    public void testGetFound() {
        String code = "DELHI_IN";
        Location location = repository.findByCode(code);

        assertThat(location).isNotNull();
        assertThat(location.getCode()).isEqualTo(code);

    }

    @Test
    public void testTrashSuccess() {
        String code = "LACA_USA";

        repository.trashByCode(code);
        Location location = repository.findByCode(code);

        assertThat(location).isNull();
    }

    @Test
    public void testAddRealtimeWeatherData() {
        String code = "NYC_USA";

        Location location = repository.findByCode(code);

        RealtimeWeather realtimeWeather = location.getRealtimeWeather();

        if (realtimeWeather == null) {
            realtimeWeather = new RealtimeWeather();
            realtimeWeather.setLocation(location);
            location.setRealtimeWeather(realtimeWeather);
        }

        realtimeWeather.setTemperature(-1);
        realtimeWeather.setHumidity(30);
        realtimeWeather.setPrecipation(40);
        realtimeWeather.setStatus("Snowy");
        realtimeWeather.setWindSpeed(15);
        realtimeWeather.setLastUpdated(new Date(System.currentTimeMillis()));

        Location updatedLocation = repository.save(location);

        assertThat(updatedLocation.getRealtimeWeather().getLocationCode()).isEqualTo(code);
    }
}
