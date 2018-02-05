package com.yakarex.animequiz.activities;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.yakarex.animequiz.R;

import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;


public class SplashActivity extends Activity {

    // Set the display time, in milliseconds (or extract it out as a configurable parameter)
    private final int SPLASH_DISPLAY_LENGTH = 1600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        servicesOk();

        Intent mainIntent = new Intent(SplashActivity.this, MainFragActivity.class);
        overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_up_animation);
        startActivity(mainIntent);
    }

    private boolean servicesOk() {
        int isAvailable = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;

        } else {
            Toast.makeText(this, "Verify Google Play Service installation", Toast.LENGTH_LONG)
                    .show();
        }
        return false;
    }

}
