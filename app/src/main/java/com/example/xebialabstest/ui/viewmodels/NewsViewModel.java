package com.example.xebialabstest.ui.viewmodels;

import com.example.xebialabstest.BuildConfig;
import com.example.xebialabstest.base.BaseViewHolder;
import com.example.xebialabstest.base.BaseViewModel;
import com.example.xebialabstest.data.api.apiresponse.NewsListing;
import com.example.xebialabstest.data.api.network.GenericResponseHandler;
import com.example.xebialabstest.data.repo.NewsListRepo;
import com.example.xebialabstest.utils.AppConstants;

import java.util.WeakHashMap;

public class NewsViewModel extends BaseViewModel {

    private final GenericResponseHandler<WeakHashMap<String,Object>, NewsListing> apiHanlder;
    private NewsListRepo newsListRepo;


    public GenericResponseHandler<WeakHashMap<String,Object>, NewsListing> getApiHanlder() {
        return apiHanlder;
    }

    public NewsViewModel() {
        super();
        newsListRepo=new NewsListRepo();
        apiHanlder=newsListRepo.callNewsApi(7);
    }


    public   void  callApi(){
        WeakHashMap<String,Object> params=new WeakHashMap<>();
        params.put(AppConstants.API_KEY,BuildConfig.API_KEY);
        apiHanlder.callApi(params,AppConstants.RC_POPULAR_NEWS);
    }





}
