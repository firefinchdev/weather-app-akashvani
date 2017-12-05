package com.firefinch.akashvani.retrofitAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiService {
    private ApiService () {}

    private static API api = null;

    public static API getAPI () {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.darksky.net/forecast/d623447086333271139294962a227b91/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(API.class);
        }

        return api;
    }
}
