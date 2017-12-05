package com.firefinch.akashvani.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nitin on 10/5/2017.
 */

public class WeatherDetailed {

    @SerializedName("time")
    Integer time;
    @SerializedName("summary")
    String summary;
    @SerializedName("icon")
    String icon;
    @SerializedName("precipIntensity")
    Double precipIntensity;
    @SerializedName("temperature")
    Double temperature;
    @SerializedName("humidity")
    Double humidity;
    @SerializedName("windSpeed")
    Double windSpeed;
    @SerializedName("windGust")
    Double windGust;

    public WeatherDetailed(Integer time, String summary, String icon, Double precipIntensity, Double temperature, Double humidity, Double windSpeed, Double windGust) {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.precipIntensity = precipIntensity;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
    }



    public Integer getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public Double getPrecipIntensity() {
        return precipIntensity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getWindGust() {
        return windGust;
    }
}
