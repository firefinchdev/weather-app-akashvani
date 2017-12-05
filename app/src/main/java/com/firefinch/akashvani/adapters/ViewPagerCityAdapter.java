package com.firefinch.akashvani.adapters;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ProgressBar;

import com.firefinch.akashvani.db.tables.LocationTable;
import com.firefinch.akashvani.fragments.FragmentSingleCity;
import com.firefinch.akashvani.models.Location;
import com.firefinch.akashvani.utils.Consts;

import java.util.ArrayList;

/**
 * Created by Nitin on 10/5/2017.
 */

public class ViewPagerCityAdapter extends FragmentPagerAdapter {
    SQLiteDatabase db;
    ArrayList<Location> locations;
    int size;
    ProgressBar progressBar;
    public ViewPagerCityAdapter(FragmentManager fm, SQLiteDatabase db) {
        super(fm);
        this.db = db;
        locations = LocationTable.getAllLocations(db);
        size = LocationTable.getCount(db);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new FragmentSingleCity();
        Bundle bundle = new Bundle();
        bundle.putDouble(Consts.LATITUDE,locations.get(position).getLatitude());
        bundle.putDouble(Consts.LONGITUDE,locations.get(position).getLongitude());
        fragment.setArguments(bundle);
        return fragment;
        /*switch (position){
            case 0:return new FragmentSingleCity();//TODO:
            case 1:return new FragmentSingleCity();
            case 2:return new FragmentSingleCity();
        }*/

    }

    @Override
    public int getCount() {
        return size;//TODO:hardcoded
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return locations.get(position).getName();
    }
}
