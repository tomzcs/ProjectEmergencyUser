package com.example.tomz4th_chaiyot.projectemergencyuser;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by toMz4th-ChaiYot on 10/29/2016.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
