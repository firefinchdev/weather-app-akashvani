package com.firefinch.akashvani.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.firefinch.akashvani.MainActivity;
import com.firefinch.akashvani.R;
import com.firefinch.akashvani.utils.PrefManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch fahrenTemp = (Switch) findViewById(R.id.fahrenTemp);
        final PrefManager pm = new PrefManager(this);
        fahrenTemp.setChecked(pm.isTempFahrenheit());
        fahrenTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pm.setTempFahrenheit(isChecked);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        super.onBackPressed();
    }
}
