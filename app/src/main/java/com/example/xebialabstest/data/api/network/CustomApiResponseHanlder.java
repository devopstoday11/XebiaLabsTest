package com.example.xebialabstest.data.api.network;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public abstract class CustomApiResponseHanlder<Req, Res> {

    private MutableLiveData<Res> mResponse;
    private MutableLiveData<CustomException> mReportedFailure;
    public CustomException customException = new CustomException();

    public CustomApiResponseHanlder() {
        this.mResponse = new MutableLiveData<>();
        this.mReportedFailure = new MutableLiveData<>();
    }

    public LiveData<Res> isSuccessful() {
        return mResponse;
    }

    public LiveData<CustomException> isFailed() {
        return mReportedFailure;
    }

    public void callApi(Req params, int requestCode, int position, String tag) {
        proceeedApiCall(mResponse, mReportedFailure, params, requestCode,position,tag);
    }

    public abstract void proceeedApiCall(MutableLiveData<Res> successResponseLiveData,
                                         MutableLiveData<CustomException> failureResponseLiveData, Req params, int requestCode, int position, String tag);


}
