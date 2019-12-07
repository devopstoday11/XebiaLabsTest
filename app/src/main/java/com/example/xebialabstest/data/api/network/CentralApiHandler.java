package com.example.xebialabstest.data.api.network;

import androidx.annotation.NonNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CentralApiHandler<T> implements Callback<T> {
    public static final int AUTH_FAILED = 99;
    public static final int NO_INTERNET = 9;


    public abstract void onSuccess(T body);

    public abstract void onFailure(CustomException t);
    public abstract void onError(int code, NotOkException failureResponse);



    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.body() != null && response.isSuccessful()) {
            onSuccess(response.body());

        } else {
            onError(response.code(),NotOkException.newFromResponse(response));
        }

    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException)
           onFailure(getResponseForNoNetwork());
        else
        onFailure(getFailureResponse(t));
    }

    private CustomException getResponseForNoNetwork() {
        CustomException failureResponse = new CustomException();
        failureResponse.setMessage("No Network");
        failureResponse.setCustomErrorCode(NO_INTERNET);
        return failureResponse;
    }

    private CustomException getFailureResponse(Throwable t) {
        CustomException failureResponse = new CustomException();
        failureResponse.setException(t);
        return failureResponse;
    }




}
