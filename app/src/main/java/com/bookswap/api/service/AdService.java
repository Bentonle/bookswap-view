package com.bookswap.api.service;

import com.bookswap.model.StdResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AdService {

    @Multipart
    @POST("ad/create/{username}")
    Call<HashMap<String, Object>> createNewAd(
            @Path("username") String username ,
            @Header("Authorization") String token,
            @Part("ad") HashMap<String, Object> body,
            @Part MultipartBody.Part file);
}
