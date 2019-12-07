package com.example.xebialabstest.data.api.network;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;


/**
 * Generic Api  calling class
 *
 * @param <Req,Res> --> can be any response type object required
 */
public abstract class GenericResponseHandler<Req, Res> {
    private MutableLiveData<Res> mResponse;
    private MutableLiveData<CustomException> mReportedFailure;
    private Req params;



    public GenericResponseHandler() {
        this.mResponse = new MutableLiveData<>();
        this.mReportedFailure = new MutableLiveData<>();
    }

    public LiveData<Res> isSuccessful() {
        return mResponse;
    }

    public LiveData<CustomException> isFailed() {
        return mReportedFailure;
    }

    public void callApi(@Nullable Req params, int reqCode) {
        Log.e("Current Req is: ", new Gson().toJson(params));
        proceeedApiCall(mResponse, mReportedFailure, params, reqCode);
    }


    protected abstract void proceeedApiCall(MutableLiveData<Res> successResponseLiveData,
                                            MutableLiveData<CustomException> failureResponseLiveData, Req params, int reqCode);
}