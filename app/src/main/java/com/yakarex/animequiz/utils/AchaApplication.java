package com.yakarex.animequiz.utils;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.yakarex.animequiz.R;
import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;

/**
 * Created by aromero on 28/01/16.
 */
public class AchaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Paper.init(this);

        MobileAds.initialize(this, this.getString(R.string.admob_app_id));

    }

}
