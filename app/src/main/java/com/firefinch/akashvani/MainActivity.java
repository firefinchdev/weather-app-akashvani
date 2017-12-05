package com.firefinch.akashvani;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firefinch.akashvani.activities.AboutActivity;
import com.firefinch.akashvani.activities.CityManagerActivity;
import com.firefinch.akashvani.activities.FirstCitySelectActivity;
import com.firefinch.akashvani.activities.SettingsActivity;
import com.firefinch.akashvani.adapters.ViewPagerCityAdapter;
import com.firefinch.akashvani.db.LocationDbHelper;
import com.firefinch.akashvani.db.tables.LocationTable;
import com.firefinch.akashvani.fragments.FragmentFirstCitySelect;
import com.firefinch.akashvani.interfaces.onFirstLaunchCompleted;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,onFirstLaunchCompleted {

    FragmentFirstCitySelect fragFirst;
    TabLayout tabLayout;
    ViewPager viewPager;
    int cityCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase locationDb = new LocationDbHelper(this).getWritableDatabase();
        cityCount = LocationTable.getCount(locationDb);
        if(cityCount == 0) {

            //SEND AN INTENT TO FIRSTLAUNCH WITH Intent.putExtra(locationDb)

            startActivity(new Intent(this,FirstCitySelectActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            /*LocationTable.insertLocation(locationDb, new Location("Delhi", 28.6139, 77.209));

            Bundle bundle = null;
            try {
                bundle = Util.getLatLng(this,"DELHI");
                LocationTable.insertLocation(locationDb,
                        new Location("DELHI", bundle.getDouble(Consts.LATITUDE), bundle.getDouble(Consts.LONGITUDE)));
            } catch (IOException e) {
                Toast.makeText(this, "Invalid Location Deleted", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }*/



        }




        tabLayout = (TabLayout) findViewById(R.id.tabView);
        //  tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FirstCitySelectActivity.class));
            }
        });
        fab.setVisibility(View.GONE);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verticalViewPager.setCurrentItem(1-verticalViewPager.getCurrentItem(),true);
                fab.setImageResource(Util.getFabDirection(verticalViewPager.getCurrentItem()));
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager)findViewById(R.id.cityViewPager);
        viewPager.setAdapter(new ViewPagerCityAdapter(getSupportFragmentManager(),locationDb));
        viewPager.setOffscreenPageLimit(cityCount-1);
        tabLayout.setupWithViewPager(viewPager);
        //setTabStretching();
        //getSupportFragmentManager().beginTransaction().add(R.id.city_container,new FragmentSingleCity()).commit();

        /*VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalViewPager);
        DISCARDED_VerticalViewPagerAdapter verticalViewPagerAdapter = new DISCARDED_VerticalViewPagerAdapter(getSupportFragmentManager());
        verticalViewPager.setAdapter(verticalViewPagerAdapter);
        tabLayout.setupWithViewPager(verticalViewPager);*/

        //getSupportFragmentManager().beginTransaction().add(R.id.weather_container,new DISCARDED_FragmentWeatherDetailed()).commit();
        //getSupportFragmentManager().beginTransaction().add(R.id.weather_container,new DISCARDED_FragmentWeatherForcast()).commit();

        /*RecyclerView rvWeather = (RecyclerView) findViewById(R.id.rvWeather);
        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        final ForcastAdapter tempAdap = new ForcastAdapter(this,new ArrayList<Weather>());
        rvWeather.setAdapter(tempAdap);

        ApiService.getAPI().getCityWeather("28.6139","77.209").enqueue(new Callback<WeatherPOJO>() {
            @Override
            public void onResponse(Call<WeatherPOJO> call, Response<WeatherPOJO> response) {
                ArrayList<Weather> weather = response.body().getDaily().getData();
                tempAdap.update(weather);
            }

            @Override
            public void onFailure(Call<WeatherPOJO> call, Throwable t) {

            }
        });*/





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_add_city) {
            startActivity(new Intent(this, CityManagerActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_add_city:
                startActivity(new Intent(this,CityManagerActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void successFirstLaunch() {

        recreate();
        //getSupportFragmentManager().beginTransaction().remove(fragFirst);

    }

    //WASTE FUNCTION FOR NOW
    public void setTabStretching(){
        /*DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        Log.d("ff", "setTabStretching: fuckkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        int width = metrics.widthPixels;

        int height = metrics.heightPixels;
        double wi = (double) width / (double) metrics.xdpi;
        double hi = (double) height / (double) metrics.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);


        /*if (screenInches < 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        float myTabLayoutSize = 360;

        if (DeviceInfo.getWidthDP(this) >= myTabLayoutSize ){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }*/
    }

}
