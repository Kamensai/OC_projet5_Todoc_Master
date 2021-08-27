package com.cleanup.todoc;

import android.app.Application;
import android.content.res.Resources;

public class App extends Application {
    private static App mInstance;
    private static Resources res;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        res = getResources();
    }

    // Help to get AppInstance anywhere in the code
    public static App getInstance() {
        return mInstance;
    }

    // Help to get AppResources anywhere in the code (used here in the database)
    public static Resources getRes() {
        return res;
    }

}