package com.yakarex.animequiz.activities;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.yakarex.animequiz.R;
import com.yakarex.animequiz.R.id;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;


public class SplashActivity extends Activity {
	
	// Set the display time, in milliseconds (or extract it out as a configurable parameter)
    private final int SPLASH_DISPLAY_LENGTH = 1600;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	}

	
	@Override
    protected void onResume()
    {
        super.onResume();
        
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(1500);
        animation1.setStartOffset(5);
        
        ImageView splash= (ImageView) findViewById(id.challengeview);
        splash.setAnimation(animation1);
        
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    //Finish the splash activity so it can't be returned to.
                    SplashActivity.this.finish();
                    // Create an Intent that will start the main activity.
                    Intent mainIntent = new Intent(SplashActivity.this, MainFragActivity.class);
                    overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    startActivity(mainIntent);
                }
            }, SPLASH_DISPLAY_LENGTH);
        
            servicesOk();
            
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
