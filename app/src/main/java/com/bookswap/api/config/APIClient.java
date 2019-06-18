package com.bookswap.api.config;

import com.bookswap.api.service.UserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class APIClient {

    private final Retrofit retrofit;
    private  static APIClient mInstance;

    public APIClient() {

        this.retrofit = new Retrofit.Builder()
                //.baseUrl("http://myvmlab.senecacollege.ca:6510/bookswap-0.0.1/")
                //.baseUrl("http://192.168.117.1:8080/bookswap-0.0.1/")
                .baseUrl("http://10.0.2.2:8080/bookswap=0.0.1/")
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

