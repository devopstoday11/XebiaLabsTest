package com.example.xebialabstest;

import android.app.Application;

import com.example.xebialabstest.data.AppDataManager;

public class MyApplication  extends Application {

    private  MyApplication instance;


    public MyApplication getInstance()
    {
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null)
                    instance = this;
            }
        }
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        AppDataManager.init(this);
    }


}
