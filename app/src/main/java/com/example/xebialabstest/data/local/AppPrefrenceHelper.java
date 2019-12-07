package com.example.xebialabstest.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefrenceHelper  {


    private SharedPreferences mPrefs;
    private static  AppPrefrenceHelper instance;


    private static  final  String USER_NAME="userName";
    private static  final  String PREF_NAME="sparsh";

    private AppPrefrenceHelper(Context context) {
        initPreference(context);


    }


    public static AppPrefrenceHelper init(Context context) {
        if (instance == null) {
            synchronized (AppPrefrenceHelper.class) {
                if (instance == null)
                    instance = new AppPrefrenceHelper(context);
            }
        }
        return instance;
    }


    public static AppPrefrenceHelper getInstance() {
        return instance;
    }


    private void initPreference(Context context) {
        if (mPrefs == null) {
            mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }
    public String getString(String key) {
        return mPrefs.getString(key,"");
    }


    public void putString(String key, String value) {
        SharedPreferences.Editor editor=mPrefs.edit();
        editor.putString(key,value);
        editor.apply();

    }

    //todo add other utility methods u wan
}
