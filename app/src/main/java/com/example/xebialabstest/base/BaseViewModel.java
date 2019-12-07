package com.example.xebialabstest.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.example.xebialabstest.data.AppDataManager;
import com.example.xebialabstest.utils.SnackbarMessage;


public class BaseViewModel extends ViewModel {

    private final AppDataManager mDataManager;

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private SnackbarMessage snackbarMessage = new SnackbarMessage();


    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public BaseViewModel() {
        mDataManager=AppDataManager.getInstance();
    }

    public AppDataManager getmDataManager() {
        return mDataManager;
    }
    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

}
