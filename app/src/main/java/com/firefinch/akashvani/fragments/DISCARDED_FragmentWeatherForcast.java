package com.firefinch.akashvani.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firefinch.akashvani.R;
import com.firefinch.akashvani.adapters.ForcastAdapter;
import com.firefinch.akashvani.models.Weather;
import com.firefinch.akashvani.models.WeatherPOJO;
import com.firefinch.akashvani.retrofitAPI.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nitin on 10/5/2017.
 */
//ABANDONED
public class DISCARDED_FragmentWeatherForcast extends Fragment {

    RecyclerView rvWeatherList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_weather_forcast,container,false);
        if(view == null){
            Log.d("DEBUG", "onCreateView: NULL");
        }
        else Log.d("DEBUG", "onCreateView: NOT NULL");
        rvWeatherList = (RecyclerView) view.findViewById(R.id.rvWeatherForcast);

        rvWeatherList.setLayoutManager(new LinearLayoutManager(getContext()));
        final ForcastAdapter forcastAdapter = new ForcastAdapter(getContext(),new ArrayList<Weather>());
        rvWeatherList.setAdapter(forcastAdapter);
        rvWeatherList.setNestedScrollingEnabled(false);



        //forcastAdapter.update(new WeatherData().getData());   //TODO: What to do? Problem with this statement is that
                                                                //An empty ArrList is returned because ASync Delay
        ApiService.getAPI().getCityWeather("28.6139","77.209").enqueue(new Callback<WeatherPOJO>() {
            @Override
            public void onResponse(Call<WeatherPOJO> call, Response<WeatherPOJO> response) {
                ArrayList<Weather> weather = response.body().getDaily().getData();
                forcastAdapter.update(weather);
            }

            @Override
            public void onFailure(Call<WeatherPOJO> call, Throwable t) {

            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*Activity activity;
        if(context instanceof Activity){
            activity = (Activity) context;
        }*/
    }
}
