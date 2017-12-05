package com.firefinch.akashvani.retrofitAPI;

import android.util.Log;

import com.firefinch.akashvani.models.Weather;
import com.firefinch.akashvani.models.WeatherPOJO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nitin on 10/5/2017.
 */
//ABANDONED FOR NOW
public class WeatherData {

    private ArrayList<Weather> weather = new ArrayList<>();
    public void refreshData(){
        ApiService.getAPI().getCityWeather("28.6139","77.209").enqueue(new Callback<WeatherPOJO>() {
            @Override
            public void onResponse(Call<WeatherPOJO> call, Response<WeatherPOJO> response) {
                weather.clear();
                ArrayList<Weather> tempweather = response.body().getDaily().getData();
                Log.d("hhhhhhhh", "onResponse: ffffffffffff" + tempweather.size());
                for (Weather i : tempweather){
                    Log.d("ggg", "onResponse: yoooooooooooo");
                    weather.add(i);

                }

            }

            @Override
            public void onFailure(Call<WeatherPOJO> call, Throwable t) {

            }
        });
    }
    public ArrayList<Weather> getData(){
        if(weather.size() == 0)refreshData();
        Log.d("hhh", "getData: gggggggggg"+ weather.size());
        return weather;
    }

}
