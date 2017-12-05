package com.firefinch.akashvani.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Nitin on 10/5/2017.
 */

public class WeatherPOJOHelper {
    @SerializedName("data")
    ArrayList<Weather> data;

    public ArrayList<Weather> getData() {
        return data;
    }

    public WeatherPOJOHelper(ArrayList<Weather> data) {

        this.data = data;
    }
}
