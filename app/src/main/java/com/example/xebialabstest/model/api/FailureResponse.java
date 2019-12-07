package com.example.xebialabstest.model.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FailureResponse {

    @SerializedName("MESSAGE")
    @Expose
    private String message;
    @SerializedName("CODE")
    @Expose
    private int code;

    public FailureResponse() {
    }

    public FailureResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
