package com.example.xebialabstest.data.api;




import com.example.xebialabstest.data.api.apiresponse.NewsListing;

import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


interface ApiClient {

    @FormUrlEncoded
    @POST("/api/v1/operator/login")
    Call<ResponseBody> dummy(@FieldMap HashMap<String, String> params);


    //@GET("/svc/mostpopular/v2/viewed/{period}.json?api-key={apiKey}")
    @GET("/svc/mostpopular/v2/viewed/{period}.json")
    Call<NewsListing> getMostPopularNews(@Path("period")Integer period,@QueryMap WeakHashMap<String, Object> params);

}