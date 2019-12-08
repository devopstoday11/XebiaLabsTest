package com.example.xebialabstest;

import android.app.Application;

import com.example.xebialabstest.data.AppDataManager;

public class MyApplication  extends Application {

    private static MyApplication instance;


    public static MyApplication getInstance()
    {

        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        AppDataManager.init(this);
    }


}
