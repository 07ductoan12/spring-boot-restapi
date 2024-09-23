package com.toan.weatherforecast.realtime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toan.weatherforecast.GeolocationException;
import com.toan.weatherforecast.GeolocationService;
import com.toan.weatherforecast.common.Location;
import com.toan.weatherforecast.common.RealtimeWeather;
import com.toan.weatherforecast.location.LocationNotFoundException;

@WebMvcTest(RealtimeWeatherController.class)
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
        public void testGetShouldReturnStatus400BadRequest() throws Exception {
                Mockito.when(locationService.getLocation(Mockito.anyString())).thenThrow(GeolocationException.class);

                mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        public void testGetShouldReturnStatus404NotFound() throws Exception {
                Location location = new Location();

                Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
                Mockito.when(realtimeWeatherService.getByLocation(location)).thenThrow(LocationNotFoundException.class);

                mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                                .andExpect(MockMvcResultMatchers.status().isNotFound())
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        public void testGetShouldReturnStatus200OK() throws Exception {
                Location location = new Location();
                location.setCode("SFCA_USA");
                location.setCityName("San Franciso");
                location.setRegionName("California");
                location.setCountryName("United States of American");
                location.setCountryCode("US");

                RealtimeWeather realtimeWeather = new RealtimeWeather();
                realtimeWeather.setTemperature(12);
                realtimeWeather.setHumidity(32);
                realtimeWeather.setLastUpdated(new Date(System.currentTimeMillis()));
                realtimeWeather.setPrecipation(88);
                realtimeWeather.setStatus("Cloudy");
                realtimeWeather.setWindSpeed(5);

                realtimeWeather.setLocation(location);
                location.setRealtimeWeather(realtimeWeather);

                Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
                Mockito.when(realtimeWeatherService.getByLocation(location)).thenReturn(realtimeWeather);

                String expectedLocation = location.getCityName() + ", " + location.getRegionName() + ", "
                                + location.getCountryName();

                mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(jsonPath("$.location", is(expectedLocation)))
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        public void testGetByLocationCodeShouldReturnStatus404NotFound() throws Exception {
                String locationCode = "ABC";

                Mockito.when(realtimeWeatherService.getByLocationCode(locationCode))
                                .thenThrow(LocationNotFoundException.class);

                String requestURI = END_POINT_PATH + "/" + locationCode;

                mockMvc.perform(MockMvcRequestBuilders.get(requestURI))
                                .andExpect(MockMvcResultMatchers.status().isNotFound())
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        public void testGetByLocationCodeShouldReturnStatus200Ok() throws Exception {
                String locationCode = "SFCA_USA";

                Location location = new Location();
                location.setCode(locationCode);
                location.setCityName("San Franciso");
                location.setRegionName("California");
                location.setCountryName("United States of America");
                location.setCountryCode("US");

                RealtimeWeather realtimeWeather = new RealtimeWeather();
                realtimeWeather.setTemperature(12);
                realtimeWeather.setHumidity(32);
                realtimeWeather.setLastUpdated(new Date(System.currentTimeMillis()));
                realtimeWeather.setPrecipation(88);
                realtimeWeather.setStatus("Cloudy");
                realtimeWeather.setWindSpeed(5);

                realtimeWeather.setLocation(location);
                location.setRealtimeWeather(realtimeWeather);

                Mockito.when(realtimeWeatherService.getByLocationCode(locationCode)).thenReturn(realtimeWeather);

                String expectedLocation = location.getCityName() + ", " + location.getRegionName() + ", "
                                + location.getCountryName();

                String requestURI = END_POINT_PATH + "/" + locationCode;

                mockMvc.perform(MockMvcRequestBuilders.get(requestURI)).andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                                .andExpect(jsonPath("$.location", is(expectedLocation)))
                                .andDo(MockMvcResultHandlers.print());
        }

        @Test
        public void testUpdateShouldReturn400BadRequest() throws Exception {
                String locationCode = "ABC_US";
                String requestURI = END_POINT_PATH + "/" + locationCode;

                RealtimeWeather realtimeWeather = new RealtimeWeather();
                realtimeWeather.setTemperature(120);
                realtimeWeather.setHumidity(32);
                realtimeWeather.setLastUpdated(new Date(System.currentTimeMillis()));
                realtimeWeather.setPrecipation(88);
                realtimeWeather.setStatus("Cloudy");
                realtimeWeather.setWindSpeed(5);

                String bodyContent = mapper.writeValueAsString(realtimeWeather);

                mockMvc.perform(MockMvcRequestBuilders.put(requestURI).contentType("application/json")
                                .content(bodyContent))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andDo(MockMvcResultHandlers.print());
        }
}