package com.firefinch.akashvani.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nitin on 10/5/2017.
 */

public class Weather {
    @SerializedName("time")
    Integer time;
    @SerializedName("summary")
    String summary;
    @SerializedName("icon")
    String icon;
    @SerializedName("temperatureHigh")
    Double temperatureHigh;

    public Weather(Integer time, String summary, String icon, Double temperatureHigh) {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.temperatureHigh = temperatureHigh;
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

    public Double getTemperatureHigh() {
        return temperatureHigh;
    }
}
