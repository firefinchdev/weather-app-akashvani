package com.firefinch.akashvani.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nitin on 10/5/2017.
 */

public class WeatherPOJO {
    @SerializedName("timezone")
    String timezone;
    @SerializedName("currently")
    WeatherDetailed currently;
    @SerializedName("daily")
    WeatherPOJOHelper daily;

    public String getTimezone() {
        return timezone;
    }

    public WeatherDetailed getCurrently() {
        return currently;
    }

    public WeatherPOJOHelper getDaily() {
        return daily;
    }

    public WeatherPOJO(String timezone, WeatherDetailed currently, WeatherPOJOHelper daily) {
        this.timezone = timezone;
        this.currently = currently;
        this.daily = daily;
    }
}
