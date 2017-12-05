package com.firefinch.akashvani.utils;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.firefinch.akashvani.R;
import com.firefinch.akashvani.db.tables.LocationTable;
import com.firefinch.akashvani.models.Location;
import com.firefinch.akashvani.permissions.Permission;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Nitin on 10/5/2017.
 */

public class Util {
    private Util() {
    }

    static String keyDarkSkyApi = "d623447086333271139294962a227b91";

    public static void updateKey(String newKey) {
        keyDarkSkyApi = newKey;
    }

    public static void resetKey() {
        keyDarkSkyApi = "d623447086333271139294962a227b91";
    }

    public static int getFabDirection(int i) {
        switch (i) {
            case Consts.DETAILED_VIEW:
                return R.drawable.arrow_down;
            case Consts.FORCAST_VIEW:
                return R.drawable.arrow_up;
            default:
                return 0;
        }
    }

    public static Bundle getLatLng(Context context, String location) throws IOException {
        ArrayList<Double> latitudeList = new ArrayList<>(); // A list to save the coordinates if they are available
        ArrayList<Double> longitudeList = new ArrayList<>(); // A list to save the coordinates if they are available
        int count = 0;
        if (Geocoder.isPresent()) {
            //try {
            Geocoder gc = new Geocoder(context);

            List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects
            for (Address a : addresses) {
                if (a.hasLatitude() && a.hasLongitude()) {
                    count++;
                    latitudeList.add(new Double(a.getLatitude()));
                    longitudeList.add(new Double(a.getLongitude()));
                }
            }
           /* } catch (IOException e) {
                // handle the exception
            }*/
        }
        if (count == 0) return null;
        Bundle bundle = new Bundle();
        bundle.putDouble(Consts.LATITUDE, latitudeList.get(0));
        bundle.putDouble(Consts.LONGITUDE, longitudeList.get(0));
        return bundle;
    }

    public static boolean insertCityFromName(Context context, String cityInput, SQLiteDatabase locationDb) {
        if (cityInput.length() > 0) {
            Bundle bundle = null;
            try {
                bundle = Util.getLatLng(context, cityInput);
                if (bundle == null) {
                    return false;
                }
                LocationTable.insertLocation(locationDb,
                        new Location(cityInput, bundle.getDouble(Consts.LATITUDE), bundle.getDouble(Consts.LONGITUDE)));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean insertCityFromLatLng(Context context,double latitute,double longitude, SQLiteDatabase locationDb) throws IOException {
        if (Geocoder.isPresent()) {
            //try {
            Geocoder gc = new Geocoder(context);
            List<Address> loc = gc.getFromLocation(latitute, longitude, 1);
            if(loc==null || loc.size()==0)return false;
            LocationTable.insertLocation(locationDb,
                    new Location(loc.get(0).getLocality(), latitute, longitude));
            return true;
        }
        return false;
    }
    public static boolean insertCityFromLatLngName(Context context,double latitute,double longitude,String name, SQLiteDatabase locationDb) throws IOException {

            LocationTable.insertLocation(locationDb,
                    new Location(name, latitute, longitude));
            return true;
    }

    public static String getDate(long milliSeconds, int i) {
        String dateFormat = "EEEEE MMMMM yyyy HH:mm:ss";
        switch (i) {
            case 1:
                dateFormat = "EEEEE MMMMM yyyy HH:mm:ss";
        }
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getTimeFromUNIX(Integer unixSeconds, int i){
        String formattedDate;
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat(); // the format of your date
        switch (i){
            case 1:
                sdf = new SimpleDateFormat("EEEE, MMM dd, hh:mm a");
                //sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                break;
            case 2:
                sdf = new SimpleDateFormat("EEEE \n MMM dd");
        }
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static android.location.Location getCurrentLoc(Activity activity) {

        final android.location.Location[] mloc = {null};

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                mloc[0] = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        LocationManager mLocationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        if(Permission.grantLocPermission(activity,1)) {
            //noinspection MissingPermission
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000,
                    0, locationListener);
            mloc[0] = mLocationManager.getLastKnownLocation(LOCATION_SERVICE);
        }
        else{
            Toast.makeText(activity, "Permissions Needed !!", Toast.LENGTH_SHORT).show();
            return null;
        }
        return mloc[0];
    }

    public static Double getFehreneitFromCelcius(Double d){
        return ((9.0/5.0)*d + 32);
    }
}
