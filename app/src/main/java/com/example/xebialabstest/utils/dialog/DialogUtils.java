package com.example.xebialabstest.utils.dialog;



public class DialogUtils {
    private static DialogUtils instance;


    public synchronized static DialogUtils getInstance() {
        if (instance == null)
            instance = new DialogUtils();
        return instance;
    }


}
