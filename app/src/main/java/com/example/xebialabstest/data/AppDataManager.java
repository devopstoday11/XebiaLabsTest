package com.example.xebialabstest.data;

import android.content.Context;

import com.example.xebialabstest.data.api.ApiManager;
import com.example.xebialabstest.data.local.AppPrefrenceHelper;


public class AppDataManager {

    private static AppDataManager instance;
    private final ApiManager apiManager;
    private final AppPrefrenceHelper preferenceManager;

    private AppDataManager(Context context) {
        apiManager = ApiManager.init();
        preferenceManager = AppPrefrenceHelper.init(context);

    }

    /**
     * method to initialize Data Manager
     */
    public static synchronized void init(Context context) {
        if (instance == null) {
            synchronized (AppDataManager.class) {
                if (instance == null)
                    instance = new AppDataManager(context);
            }
        }

    }

    /**
     * @return instance of {@link AppDataManager}
     */
    public static AppDataManager getInstance() {
        return instance;
    }

    /**
     * method to get value from preference
     */
    public String getStringFromPreference(String key) {
        return preferenceManager.getString(key);
    }

    /**
     * method to save value in preference
     */
    public void setStringInPreference(String key, String value) {
        preferenceManager.putString(key, value);
    }


}
