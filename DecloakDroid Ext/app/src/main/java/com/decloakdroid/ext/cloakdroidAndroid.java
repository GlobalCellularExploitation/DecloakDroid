package com.decloakdroid.ext;

import android.app.Application;
import android.content.Context;

public class cloakdroidAndroid extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        cloakdroidAndroid.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return cloakdroidAndroid.context;
    }
}
