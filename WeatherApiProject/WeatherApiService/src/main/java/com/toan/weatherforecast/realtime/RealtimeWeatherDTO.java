package com.toan.weatherforecast.realtime;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RealtimeWeatherDTO {

    private String location;

    private int temperature;
    private int humidity;
    private int precipitation;

    @JsonProperty("wind_speed")
    private int windSpeed;

    private String status;

    @JsonProperty("last_updated")
    private Date lastUpdated;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPrecipation() {
        return precipitation;
    }

    public void setPrecipation(int precipitation) {
        this.precipitation = precipitation;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
