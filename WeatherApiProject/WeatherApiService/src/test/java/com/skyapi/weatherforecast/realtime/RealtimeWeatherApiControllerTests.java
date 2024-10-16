package com.skyapi.weatherforecast.realtime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.GeoLocationException;
import com.skyapi.weatherforecast.GeolocationService;
import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealtimeWeather;
import com.skyapi.weatherforecast.location.LocationNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

/** RealtimeWeatherApiControllerTests */
@WebMvcTest(RealtimeWeatherApiController.class)
public class RealtimeWeatherApiControllerTests {

    private static final String END_POINT_PATH = "/v1/realtime";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    RealtimeWeatherService realtimeWeatherService;
    @MockBean
    GeolocationService locationService;

    @Test
    public void testGetShoudReturnStatus400BadRequest() throws Exception {
        Mockito.when(locationService.getLocation(Mockito.anyString()))
                .thenThrow(GeoLocationException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void testGetShoudReturnStatus404NotFound() throws Exception {
        Location location = new Location();
        Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
        Mockito.when(realtimeWeatherService.getByLocation(location))
                .thenThrow(LocationNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH)).andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetShoudReturnStatus200OK() throws Exception {
        Location location = new Location();
        location.setCode("SFCA_USA");
        location.setCityName("San Franciso");
        location.setRegionName("Canifornia");
        location.setCountryName("United States of America");
        location.setCountryCode("US");

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(12);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setLastUpdated(new Date());
        realtimeWeather.setPrecipitation(88);
        realtimeWeather.setStatus("Cloudy");
        realtimeWeather.setWindSpeed(5);

        realtimeWeather.setLocation(location);
        location.setRealtimeWeather(realtimeWeather);

        Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
        Mockito.when(realtimeWeatherService.getByLocation(location)).thenReturn(realtimeWeather);

        String expectedLocation = location.getCityName() + ", " + location.getRegionName() + ", "
                + location.getCountryName();

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.location", is(expectedLocation))).andDo(print());
    }

    @Test
    public void testGetByLocationCodeShouldReturnStatus404NotFound() throws Exception {
        String locationCode = "ACB_XYZ";

        Mockito.when(realtimeWeatherService.getByLocationCode(locationCode))
                .thenThrow(LocationNotFoundException.class);

        String requestURI = END_POINT_PATH + "/" + locationCode;
        mockMvc.perform(MockMvcRequestBuilders.get(requestURI)).andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetByLocationCodeShouldReturnStatus200OK() throws Exception {
        String locationCode = "SFCA_USA";

        Location location = new Location();
        location.setCode(locationCode);
        location.setCityName("San Franciso");
        location.setRegionName("Canifornia");
        location.setCountryName("United States of America");
        location.setCountryCode("US");

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(12);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setLastUpdated(new Date());
        realtimeWeather.setPrecipitation(88);
        realtimeWeather.setStatus("Cloudy");
        realtimeWeather.setWindSpeed(5);

        realtimeWeather.setLocation(location);
        location.setRealtimeWeather(realtimeWeather);

        Mockito.when(realtimeWeatherService.getByLocationCode(locationCode))
                .thenReturn(realtimeWeather);

        String expectedLocation = location.getCityName() + ", " + location.getRegionName() + ", "
                + location.getCountryName();

        String requestURI = END_POINT_PATH + "/" + locationCode;

        mockMvc.perform(MockMvcRequestBuilders.get(requestURI)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.location", is(expectedLocation))).andDo(print());
    }

    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        String locationCode = "ABC_XYZ";
        String requestURI = END_POINT_PATH + "/" + locationCode;

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(120);
        realtimeWeather.setHumidity(132);
        realtimeWeather.setPrecipitation(188);
        realtimeWeather.setStatus("Cl");
        realtimeWeather.setWindSpeed(500);

        String bodyContent = mapper.writeValueAsString(realtimeWeather);

        mockMvc.perform(MockMvcRequestBuilders.put(requestURI).contentType("application/json")
                .content(bodyContent)).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        String locationCode = "ABC_XYZ";
        String requestURI = END_POINT_PATH + "/" + locationCode;

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(20);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setPrecipitation(88);
        realtimeWeather.setStatus("Cloudy");
        realtimeWeather.setWindSpeed(5);

        Mockito.when(realtimeWeatherService.update(locationCode, realtimeWeather))
                .thenThrow(LocationNotFoundException.class);

        String bodyContent = mapper.writeValueAsString(realtimeWeather);

        mockMvc.perform(MockMvcRequestBuilders.put(requestURI).contentType("application/json")
                .content(bodyContent)).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void testUpdateShouldReturn200OK() throws Exception {
        String locationCode = "SFCA_USA";
        String requestURI = END_POINT_PATH + "/" + locationCode;
        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(20);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setPrecipitation(88);
        realtimeWeather.setStatus("Cloudy");
        realtimeWeather.setWindSpeed(5);
        realtimeWeather.setLastUpdated(new Date());


        Location location = new Location();
        location.setCode(locationCode);
        location.setCityName("San Franciso");
        location.setRegionName("Canifornia");
        location.setCountryName("United States of America");
        location.setCountryCode("US");

        realtimeWeather.setLocation(location);
        location.setRealtimeWeather(realtimeWeather);

        Mockito.when(realtimeWeatherService.update(locationCode, realtimeWeather))
                .thenReturn(realtimeWeather);

        String bodyContent = mapper.writeValueAsString(realtimeWeather);
        String expectedLocation = location.getCityName() + ", " + location.getRegionName() + ", "
                + location.getCountryName();

        mockMvc.perform(MockMvcRequestBuilders.put(requestURI).contentType("application/json")
                .content(bodyContent)).andExpect(status().isOk())
                .andExpect(jsonPath("$.location", is(expectedLocation))).andDo(print());
    }
}
