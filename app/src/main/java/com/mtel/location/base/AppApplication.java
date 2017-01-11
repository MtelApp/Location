package com.mtel.location.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bibu on 16/12/28.
 */

public class AppApplication extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mApplicationContext;
    }
}
