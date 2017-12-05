package com.firefinch.akashvani.utils;

/**
 * Created by WELLCOME on 07-10-2017.
 */


import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "akashvani";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_TEMP_FAHRENHEIT = "IsTempFahrenheit";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setTempFahrenheit(boolean temp) {
        editor.putBoolean(IS_TEMP_FAHRENHEIT, temp);
        editor.commit();
    }
    public boolean isTempFahrenheit() {
        return pref.getBoolean(IS_TEMP_FAHRENHEIT, true);
    }

}

