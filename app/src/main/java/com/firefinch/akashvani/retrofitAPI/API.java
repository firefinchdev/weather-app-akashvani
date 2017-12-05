package com.firefinch.akashvani.retrofitAPI;



import com.firefinch.akashvani.models.WeatherPOJO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by arnav on 9/10/2017.
 */

public interface API {

    @GET("{latitude},{longitude}?units=si")
    Call<WeatherPOJO> getCityWeather(@Path("latitude") String latitude,@Path("longitude") String longitude);


}

