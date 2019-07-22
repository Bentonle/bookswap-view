package com.bookswap.api.config;

import com.bookswap.api.service.AdService;
import com.bookswap.api.service.UserService;
import com.bookswap.ui.user.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class APIClient {

    private final Retrofit retrofit;
    private static APIClient mInstance;
    private String BASE_URL = "http://myvmlab.senecacollege.ca:6510/bookswap-0.0.1/";
    //private String token = LoginActivity.token;


    public APIClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        //.addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(request);
            }
        });

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                //.baseUrl("http://192.168.117.1:8080/bookswap-0.0.1/")
                //.baseUrl("http://10.0.2.2:8080/bookswap=0.0.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public synchronized  APIClient getInstance() {
        if(mInstance == null){
            mInstance = new APIClient();
        }
        return mInstance;
    }

    public UserService getUserService() {
        return this.retrofit.create(UserService.class);
    }
    public AdService getAdService() {
        return this.retrofit.create(AdService.class);
    }

    /*private static final String BASE_URL = "http://myvmlab.senecacollege.ca:6510/bookswap-0.0.1/";
    //private static final String BASE_URL = "http://192.168.117.1/bookswap-0.0.2/";
    private  static APIClient mInstance;
    private final Retrofit retrofit;
    public APIClient() {
        this retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized APIClient getInstance(){
        if(mInstance == null){
            mInstance = new APIClient();
        }
        return mInstance;
    }
    public  UserService getUserService() {
        return retrofit.create(UserService.class);
    }*/
}
