package com.firefinch.akashvani.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.firefinch.akashvani.MainActivity;
import com.firefinch.akashvani.R;
import com.firefinch.akashvani.db.LocationDbHelper;
import com.firefinch.akashvani.interfaces.onFirstLaunchCompleted;
import com.firefinch.akashvani.permissions.Permission;
import com.firefinch.akashvani.utils.Consts;
import com.firefinch.akashvani.utils.Util;

import static com.firefinch.akashvani.activities.FirstCitySelectActivity.locReqCode;

/**
 * Created by Nitin on 10/6/2017.
 */

public class FragmentFirstCitySelect extends Fragment {

    onFirstLaunchCompleted interf;
    View view;
    Button btnGo;
    EditText cityInput;
    ImageView ivGps;
    Button btnPlacePicker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_first_launch,container,false);
        btnGo = view.findViewById(R.id.go_button);
        cityInput = view.findViewById(R.id.city_input);
        ivGps = view.findViewById(R.id.mtf_image);
        btnPlacePicker = view.findViewById(R.id.btn_place_picker);
    return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        interf= (onFirstLaunchCompleted) getActivity();

        final SQLiteDatabase locationDb = new LocationDbHelper(getActivity().getApplicationContext()).getWritableDatabase();
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Util.insertCityFromName(getContext(),cityInput.getText().toString(),locationDb)){
                    Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "City Not Found", Toast.LENGTH_SHORT).show();
                }
                /*if (cityInput.getText().length() > 0) {
                    Bundle bundle = null;
                    try {
                        bundle = Util.getLatLng(getContext(),cityInput.getText().toString());
                        LocationTable.insertLocation(locationDb,
                                new Location(cityInput.getText().toString(), bundle.getDouble(Consts.LATITUDE), bundle.getDouble(Consts.LONGITUDE)));
                        Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        //interf.successFirstLaunch();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Cannot find City", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Enter a City Name", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(view , "Enter a City Name" , Snackbar.LENGTH_SHORT).show();
                }*/
            }
        });

        /*OLD ivGPS OnClickListener
        ivGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.location.Location loc = Util.getCurrentLoc(getActivity());
                if(loc != null){
                    try {
                        if(Util.insertCityFromLatLng(getContext(),loc.getLatitude(),loc.getLongitude(),locationDb)){
                            Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getContext(), "City Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "IOException", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Location Not Found", Toast.LENGTH_SHORT).show();
                }

            }
        });*/
        ivGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permission.grantLocPermission(getActivity(),locReqCode);
                PlacePicker.IntentBuilder placepickerIntent = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(placepickerIntent.build(getActivity()),Consts.PLACE_PICKER_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        btnPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder placepickerIntent = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(placepickerIntent.build(getActivity()),Consts.PLACE_PICKER_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }



            }
        });


    }
}
