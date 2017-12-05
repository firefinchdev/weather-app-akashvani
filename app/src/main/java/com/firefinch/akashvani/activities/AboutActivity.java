package com.firefinch.akashvani.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.firefinch.akashvani.R;

public class AboutActivity extends AppCompatActivity {

    String libServices = "Weather API: <a href='https://darksky.net/'>Dark Sky</a> <br>"
                            + "<a href='https://github.com/google/gson'>Google-gson</a> <br>"
                            + "<a href='http://square.github.io/retrofit/'>Retrofit</a> <br>"
                            + "<a href='https://developer.android.com/topic/libraries/support-library/index.html'>Android Support: appcompat,support,design</a> <br>"
                            + "<a href='https://developers.google.com/places/android-api/placepicker'>Google Place Picker</a> <br>"
                            + "<a href='https://developers.google.com/cloud-messaging/'>Google GCM</a> <br>"
                            + "<a href='https://github.com/florent37/MaterialTextField'>Material TextView</a> <br>"
                            + "<a href='https://docs.google.com/document/d/1v5rYttr84Q98epis_ws13F39WUCBP2ZupR0V0KbcqG4/edit?usp=sharing'>PRIVACY POLICY</a> <br>";

    String icons = "Designed by <a href='https://www.flaticon.com/packs/weather-set-2'>Smashicons</a> from <a href='https://www.flaticon.com/'>Flaticon</a> <br>"
            + "Launcher Icon: <a href='https://pixabay.com/en/users/iamsushant-1781280/'>iamsushant</a> on <a href='https://pixabay.com/'>Pixabay</a>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_mail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",getString(R.string.dev_mail), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Review for " + getString(R.string.app_name));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.dev_mail)});
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        TextView tvLibServices =(TextView)findViewById(R.id.tvLibServices);
        tvLibServices.setClickable(true);
        tvLibServices.setMovementMethod(LinkMovementMethod.getInstance());
        tvLibServices.setText(Html.fromHtml(libServices));
        tvLibServices.setLinkTextColor(getResources().getColor(android.R.color.white));

        TextView tvIcons =(TextView)findViewById(R.id.tvIcons);
        tvIcons.setClickable(true);
        tvIcons.setMovementMethod(LinkMovementMethod.getInstance());
        tvIcons.setText(Html.fromHtml(icons));
        tvIcons.setLinkTextColor(getResources().getColor(android.R.color.white));
    }
}
