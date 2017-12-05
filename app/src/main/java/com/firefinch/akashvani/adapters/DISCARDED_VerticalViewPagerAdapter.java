package com.firefinch.akashvani.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.firefinch.akashvani.fragments.DISCARDED_FragmentWeatherDetailed;
import com.firefinch.akashvani.fragments.DISCARDED_FragmentWeatherForcast;

import static com.firefinch.akashvani.utils.Consts.*;

/**
 * Created by Nitin on 10/6/2017.
 */
//ABANDONED
public class DISCARDED_VerticalViewPagerAdapter extends FragmentPagerAdapter{

    public DISCARDED_VerticalViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case DETAILED_VIEW:
                fragment = new DISCARDED_FragmentWeatherDetailed();
                /*Bundle bundle = new Bundle();
                bundle.putInt("pagenumber",position+1);
                fragment.setArguments(bundle);*/
                break;
            case FORCAST_VIEW:
                fragment = new DISCARDED_FragmentWeatherForcast();
                break;

        }
       // context.getSupportFragmentManager().beginTransaction().add(R.id.weather_container,new DISCARDED_FragmentWeatherDetailed())

        return fragment;
    }

    @Override
    public int getCount() {
        return TOTAL_VIEWS;
    }
}
