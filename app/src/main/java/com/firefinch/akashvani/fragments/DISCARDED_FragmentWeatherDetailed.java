package com.firefinch.akashvani.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firefinch.akashvani.R;
import com.firefinch.akashvani.models.WeatherDetailed;
import com.firefinch.akashvani.models.WeatherPOJO;
import com.firefinch.akashvani.retrofitAPI.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nitin on 10/5/2017.
 */
//ABANDONED
public class DISCARDED_FragmentWeatherDetailed extends Fragment {

    TextView tvTime,tvSummary,tvTemperature,tvHumidity,
                tvWindSpeed,tvWindGust,tvPrecipIntensity;
    ImageView ivIcon;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_weather_detailed,container,false);
        getIds(view);
        ApiService.getAPI().getCityWeather("28.6139","77.209").enqueue(new Callback<WeatherPOJO>() {
            @Override
            public void onResponse(Call<WeatherPOJO> call, Response<WeatherPOJO> response) {
                WeatherDetailed weatherDetailed = response.body().getCurrently();
                setData(weatherDetailed);
            }

            @Override
            public void onFailure(Call<WeatherPOJO> call, Throwable t) {

            }
        });
        return view;

    }
    private void getIds(View view){
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvSummary = (TextView) view.findViewById(R.id.tvSummary);
        tvTemperature = (TextView) view.findViewById(R.id.tvTemperature);
        tvHumidity = (TextView) view.findViewById(R.id.tvHumidity);
        tvWindSpeed = (TextView) view.findViewById(R.id.tvWindSpeed);
        tvWindGust = (TextView) view.findViewById(R.id.tvWindGust);
        tvPrecipIntensity = (TextView) view.findViewById(R.id.tvPrecipIntensity);
        ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
    }
    private void setData(WeatherDetailed weatherDetailed){
        tvTime.setText(weatherDetailed.getTime().toString());
        tvSummary.setText(weatherDetailed.getSummary());
        tvTemperature.setText(String.valueOf(weatherDetailed.getTemperature().intValue()));
        tvHumidity.setText(weatherDetailed.getHumidity().toString());
        tvWindSpeed.setText(weatherDetailed.getWindSpeed().toString());
        tvWindGust.setText(weatherDetailed.getWindGust().toString());
        tvPrecipIntensity.setText(weatherDetailed.getPrecipIntensity().toString());
        //TODO: set Icon


    }
}
