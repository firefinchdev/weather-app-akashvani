package com.firefinch.akashvani.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.firefinch.akashvani.MainActivity;
import com.firefinch.akashvani.R;
import com.firefinch.akashvani.adapters.CityManagerAdapter;
import com.firefinch.akashvani.db.LocationDbHelper;
import com.firefinch.akashvani.utils.Consts;
import com.firefinch.akashvani.utils.Util;

import java.io.IOException;

public class CityManagerActivity extends AppCompatActivity {

    EditText etCityName;
    ImageView ivCityAdd;
    ImageView ivPlacePicker;
    CityManagerAdapter cityManagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        RecyclerView rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        cityManagerAdapter = new CityManagerAdapter(getApplicationContext(),CityManagerActivity.this);
        rvList.setAdapter(cityManagerAdapter);

        etCityName = (EditText) findViewById(R.id.etCityName);
        ivCityAdd = (ImageView) findViewById(R.id.ivCityAdd);
        ivPlacePicker = (ImageView) findViewById(R.id.ivPlacePicker);

        final SQLiteDatabase locationDb = new LocationDbHelper(getApplicationContext()).getWritableDatabase();

        ivCityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.insertCityFromName(CityManagerActivity.this,etCityName.getText().toString(),locationDb)){
                    Toast.makeText(CityManagerActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    cityManagerAdapter.update();
                }
                else {
                    Toast.makeText(CityManagerActivity.this, "City Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder placepickerIntent = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(placepickerIntent.build(CityManagerActivity.this), Consts.PLACE_PICKER_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0)return;
        Log.d(CityManagerAdapter.class.getName(), "onActivityResult: Yoo" + requestCode);
        //if(requestCode == Consts.PLACE_PICKER_REQUEST_CODE){
            Place place = PlacePicker.getPlace(this,data);
            SQLiteDatabase locationDb = new LocationDbHelper(getApplicationContext()).getWritableDatabase();
            try {
                boolean b = Util.insertCityFromLatLng(this,place.getLatLng().latitude,
                                        place.getLatLng().longitude,
                                        locationDb);
                if(b==false){
                    Toast.makeText(this, "Data not available for selected location", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*Util.insertCityFromLatLngName(this,place.getLatLng().latitude,
                        place.getLatLng().longitude,
                        (String) place.getName(),
                        locationDb);*/
                Toast.makeText(CityManagerActivity.this, "Auto-detected Name might be wrong. You can edit it.", Toast.LENGTH_LONG).show();
                cityManagerAdapter.update();
            } catch (IOException e) {
                e.printStackTrace();
            }


        //}
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        super.onBackPressed();
    }
}
