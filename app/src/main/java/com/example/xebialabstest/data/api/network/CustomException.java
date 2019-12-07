package com.example.xebialabstest.data.api.network;

public class CustomException extends Exception {

    private Throwable exception;
    private int requestCode;
    private int position;
    private String message;
    private int customErrorCode;
    private NotOkException apiError;

    public CustomException() {

    }

    public int getCustomErrorCode() {

        return customErrorCode;
    }

    public void setCustomErrorCode(int customErrorCode) {
        this.customErrorCode = customErrorCode;
    }

    public NotOkException getApiError() {
        return apiError;
    }

    public void setApiError(NotOkException apiError) {
        this.apiError = apiError;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
