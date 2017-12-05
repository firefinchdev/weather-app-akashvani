package com.firefinch.akashvani.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nitin on 10/6/2017.
 */

public class Location {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("latitude")
    Double latitude;
    @SerializedName("longitude")
    Double longitude;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(int id, String name, Double latitude, Double longitude) {

        this.id = id;

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
