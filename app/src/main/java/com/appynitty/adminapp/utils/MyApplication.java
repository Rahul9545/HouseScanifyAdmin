package com.appynitty.adminapp.utils;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.appynitty.adminapp.services.LocationService;
import com.pixplicity.easyprefs.library.Prefs;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        MainUtils.mainApplicationConstant = this;
    }

    public void startLocationTracking() {
        Intent intent = new Intent(this, LocationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            startForegroundService(intent);
            Log.e(TAG, "startLocationTracking: " + Prefs.getString(MainUtils.LAT, null) + ", " + Prefs.getString(MainUtils.LONG, null));
        } else {
            startService(intent);
        }
    }

    public void stopLocationTracking() {
        stopService(new Intent(this, LocationService.class));
    }
}
