package com.sbdev.mvvm;

public class WeatherModel {

    private String weatherData;

    public WeatherModel(String weatherData) {
        this.weatherData = weatherData;
    }

    public String getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(String weatherData) {
        this.weatherData = weatherData;
    }
}