package com.firefinch.akashvani.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.firefinch.akashvani.MainActivity;
import com.firefinch.akashvani.R;
import com.firefinch.akashvani.db.LocationDbHelper;
import com.firefinch.akashvani.fragments.FragmentFirstCitySelect;
import com.firefinch.akashvani.interfaces.onFirstLaunchCompleted;
import com.firefinch.akashvani.permissions.Permission;
import com.firefinch.akashvani.utils.PrefManager;
import com.firefinch.akashvani.utils.Util;

import java.io.IOException;

public class FirstCitySelectActivity extends AppCompatActivity implements onFirstLaunchCompleted {


    public static int locReqCode = 9836;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_city_select);
        getSupportActionBar().hide();
        PrefManager pm = new PrefManager(this);
        pm.setTempFahrenheit(false);
        getSupportFragmentManager().beginTransaction().add(R.id.viewContainer,new FragmentFirstCitySelect()).commit();
        Permission.grantLocPermission(this,locReqCode);
    }

    @Override
    public void successFirstLaunch() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0)return;
        if(requestCode == locReqCode)return;
        Log.d(FirstCitySelectActivity.class.getName(), "onActivityResult: Yooooo" + requestCode);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        Toast.makeText(this, "Auto-Detected Name might be wrong. You can edit it in settings", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        //}
    }
}
