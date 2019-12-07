package com.example.xebialabstest.data.api.network;

import com.example.xebialabstest.model.api.FailureResponse;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class NotOkException  {
    private int mCode;
    private FailureResponse mIssue;

    private NotOkException(int httpCode, FailureResponse resp) {
        this.mCode = httpCode;
        this.mIssue = resp;
    }

    public static NotOkException newFromResponse(Response resp) {
        NotOkException toRet = null;
        FailureResponse iResponse;
        if (resp != null && !resp.isSuccessful()) {
            ResponseBody rawRespBody = resp.errorBody();
            if (rawRespBody != null) {
                try {
                    iResponse = new Gson().fromJson(rawRespBody.string(), FailureResponse.class);
                    toRet = new NotOkException(resp.code(), iResponse);
                } catch (Exception ignored) {
                    //throw new IllegalArgumentException("Can't extract error from the response !");

                }
            }
        } else {
            throw new IllegalArgumentException("Can't extract error from the response !");
        }
        return toRet;
    }

    public int getmCode() {
        return mCode;
    }

    public void setmCode(int mCode) {
        this.mCode = mCode;
    }

    public FailureResponse getmIssue() {
        return mIssue;
    }

    public void setmIssue(FailureResponse mIssue) {
        this.mIssue = mIssue;
    }
}