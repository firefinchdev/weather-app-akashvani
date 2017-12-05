package com.firefinch.akashvani.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firefinch.akashvani.R;
import com.firefinch.akashvani.adapters.ForcastAdapter;
import com.firefinch.akashvani.models.Weather;
import com.firefinch.akashvani.models.WeatherDetailed;
import com.firefinch.akashvani.models.WeatherPOJO;
import com.firefinch.akashvani.retrofitAPI.ApiService;
import com.firefinch.akashvani.utils.Consts;
import com.firefinch.akashvani.utils.PrefManager;
import com.firefinch.akashvani.utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.round;

/**
 * Created by Nitin on 10/6/2017.
 */

public class FragmentSingleCity extends Fragment {

    Double latitude,longitude;

    TextView tvTime,tvSummary,tvTemperature,tvHumidity,
            tvWindSpeed,tvWindGust,tvPrecipIntensity;
    ImageView ivIcon;
    RecyclerView rvWeatherList;
    PrefManager pm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_single_city,container,false);

        latitude = getArguments().getDouble(Consts.LATITUDE);
        longitude = getArguments().getDouble(Consts.LONGITUDE);
        getIds(view);

        pm = new PrefManager(getContext());
        final ProgressDialog progressDialog = showLoadingProgress(view.getContext());
        /*Display display = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = display.getHeight();
        width -= getActivity().findViewById(R.id.tabView).getHeight();

        view.getLayoutParams().height = width;*/



        rvWeatherList.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
                //return super.canScrollVertically();
            }
        });
        final ForcastAdapter forcastAdapter = new ForcastAdapter(getContext(),new ArrayList<Weather>());
        rvWeatherList.setNestedScrollingEnabled(false);
        rvWeatherList.setAdapter(forcastAdapter);

        //ApiService.getAPI().getCityWeather("28.6139","77.209").enqueue(new Callback<WeatherPOJO>() {
        ApiService.getAPI().getCityWeather(latitude.toString(),longitude.toString()).enqueue(new Callback<WeatherPOJO>() {
            @Override
            public void onResponse(Call<WeatherPOJO> call, Response<WeatherPOJO> response) {
                WeatherDetailed weatherDetailed = response.body().getCurrently();
                Log.d("FragmentSingleCity", "onResponse: " + weatherDetailed);
                if(weatherDetailed!=null){
                    setData(weatherDetailed);
                }
                ArrayList<Weather> weather = response.body().getDaily().getData();
                Log.d("FragmentSingleCity", "onResponse: " + weather.size());
                forcastAdapter.update(weather);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<WeatherPOJO> call, Throwable t) {
                Toast.makeText(getContext(), "FAILED TO FETCH DATA", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


        /*view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    default: break;
                }
                return false;
            }
        });*/


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
        rvWeatherList = (RecyclerView) view.findViewById(R.id.rvWeatherForcast);
    }
    private void setData(WeatherDetailed weatherDetailed){
        tvTime.setText(Util.getTimeFromUNIX(weatherDetailed.getTime(),1));
        tvSummary.setText(weatherDetailed.getSummary());
        boolean b = pm.isTempFahrenheit();
        if(b){
            tvTemperature.setText(String.valueOf(Util.getFehreneitFromCelcius(weatherDetailed.getTemperature()).intValue()));
        } else {
            tvTemperature.setText(String.valueOf(weatherDetailed.getTemperature().intValue()));
        }
        tvHumidity.setText(String.valueOf((int)(weatherDetailed.getHumidity()*100)) + " %");
        tvWindSpeed.setText(String.valueOf(weatherDetailed.getWindSpeed()) + " km/h");
        tvWindGust.setText(String.valueOf(Math.ceil(weatherDetailed.getWindGust()*1.6)) + " km/h");
        tvPrecipIntensity.setText((int)(Math.ceil(weatherDetailed.getPrecipIntensity()*100)) + " %");
        //TODO: set Icon


    }

    public ProgressDialog showLoadingProgress(Context context){
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Fetching weather data\nJust a second...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        return progress;
    }


}
