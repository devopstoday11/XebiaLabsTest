package com.example.xebialabstest.data.api;


import com.example.xebialabstest.BuildConfig;
import com.example.xebialabstest.data.api.apiresponse.NewsListing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.QueryMap;


public class ApiManager {

    private static String BASE_URL = BuildConfig.BASE_URL;
    private static ApiManager instance;
    private final ApiClient apiClient;


    private ApiManager() {
        apiClient = getRetrofitService();
    }

    public static ApiManager init() {
        if (instance == null) {
            synchronized (ApiManager.class) {
                if (instance == null)
                    instance = new ApiManager();
            }
        }
        return instance;
    }

    public static ApiManager getInstance() {
        return instance;
    }

    private static ApiClient getRetrofitService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        return retrofit.create(ApiClient.class);
    }


    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(logging);

        httpClientBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder()
                        .method(request.method(), request.body());

                return chain.proceed(requestBuilder.build());
            }
        });

        return httpClientBuilder.build();
    }


    public Call<NewsListing> getFilteredMovieList(Integer period, WeakHashMap<String, Object> params) {
        return apiClient.getMostPopularNews(period,params);
    }



}