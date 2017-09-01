package com.yakarex.animequiz.utils;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yakarex.animequiz.R;

/**
 * Created by aromero on 28/01/16.
 */
public class AchaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        MobileAds.initialize(this, this.getString(R.string.admob_app_id));


    }
}
