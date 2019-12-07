package com.example.xebialabstest.data.repo;


import androidx.lifecycle.MutableLiveData;

import com.example.xebialabstest.data.api.ApiManager;
import com.example.xebialabstest.data.api.apiresponse.NewsListing;
import com.example.xebialabstest.data.api.network.CentralApiHandler;
import com.example.xebialabstest.data.api.network.CustomException;
import com.example.xebialabstest.data.api.network.GenericResponseHandler;
import com.example.xebialabstest.data.api.network.NotOkException;

import java.util.WeakHashMap;

public class NewsListRepo {


    public GenericResponseHandler<WeakHashMap<String,Object>, NewsListing> callNewsApi(final Integer period) {
        return new GenericResponseHandler<WeakHashMap<String,Object>, NewsListing>() {
            @Override
            protected void proceeedApiCall(final MutableLiveData<NewsListing> successResponseLiveData, final MutableLiveData<CustomException> failureResponseLiveData, WeakHashMap<String,Object> params, final int reqCode) {

                ApiManager.getInstance().getFilteredMovieList(period,params).enqueue(new CentralApiHandler<NewsListing>() {
                    @Override
                    public void onSuccess(NewsListing body) {
                        successResponseLiveData.setValue(body);
                    }

                    @Override
                    public void onError(int code, NotOkException failureResponse) {
                        failureResponseLiveData.setValue(getCustomErrorException(reqCode, code, failureResponse));
                    }

                    @Override
                    public void onFailure(CustomException customException) {

                        failureResponseLiveData.setValue(getCustomFailureException(reqCode, customException));


                    }
                });

            }


        };
    }










    private CustomException getCustomErrorException(int apiRequestCode, int errorCode, NotOkException errorException) {
        CustomException apiErrorException = new CustomException();
        apiErrorException.setCustomErrorCode(errorCode);
        apiErrorException.setRequestCode(apiRequestCode);
        apiErrorException.setApiError(errorException);
        return apiErrorException;

    }

    private CustomException getCustomFailureException(int reqCode, CustomException customException) {
        customException.setRequestCode(reqCode);
        return customException;

    }

}
