package com.example.xebialabstest.data.api.network;


import androidx.lifecycle.MutableLiveData;

public class CustomLiveData<T> extends MutableLiveData<T> {


    private  CustomLiveData customLiveData;

    private int requestCode;


    public int getRequestCode() {
        return requestCode;
    }

    public  CustomLiveData getInstance(int  requestCode) {
        this.requestCode=requestCode;

        if (customLiveData == null) {
            customLiveData = new CustomLiveData();
            return customLiveData;
        } else
            return customLiveData;
    }


    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
