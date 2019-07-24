package com.bookswap.api.service;
import com.bookswap.model.Ad;
import com.bookswap.model.StdResponse;
import com.bookswap.model.user.LoginRequest;
import com.bookswap.model.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface UserService {

    /*
     * CREATE
     */

    /**
     *
     * method for login. On successful login, the server response will contain an authentication token in
     * its header. this token must be include on future
     *
     * @param body object that contains user's password and user's login
     * @return standard json message with information about the request(status code, error message, etc)
     * that will be serialize to a object of type StdResponse.
     */

    @POST("user/login")
    Call<StdResponse> login(@Body HashMap<String,String> body);

    /**
     *
     * this methods send a new user to be register
     *
     * @param body new user object to be persisted
     * @return standard json message with information about the request(status code, error message, etc)
     * that will be serialize to a object of type StdResponse.
     */

    /*@FormUrlEncoded
    @POST("user/signup")
    Call<StdResponse> signup(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );*/
    //@Headers("Accept: application/json")

    //@Headers("Accept: application/json")
    //@POST("user/signup")
    //Call<StdResponse> signup(@Body HashMap<String, Object> body);

    @Multipart
    @POST("user/signup")
    Call<StdResponse> signup(
            @Part("user") HashMap<String, Object> body,
            @Part MultipartBody.Part file);

    /*
     * READ
     */

    /**
     *
     * returns all user information
     *
     * @param username user's username
     * @return User object
     */

    @GET("user/{username}")
    Call<HashMap<String, Object>> findUserByUsername(@Path("username") String username,@Header("Authorization") String token);

    /**
     *
     * find all ads that belongs to a specific user
     *
     * @param username user's username
     * @return ArrayList of ad
     */

    @GET("find/ad/{username}")
    Call<List<Ad>> findAdByUsername(@Path("username") String username);

    /*
     * UPDATE
     */

    /**
     *
     * method with the purpose of update user information, a user object must be passed as
     * a parameters. the user properties with null values will be ignored by the server and thus
     * the user original properties value will be remain unaltered
     *
     * @param username user's username.
     * @param body object of type User, it contains the user's properties that will be changed.
     * @return standard json message with information about the request(status code, error message, etc)
     * that will be serialize to a object of type StdResponse
     */

    @PATCH("user/update/{username}")
    Call<StdResponse> updateUserInfo(@Path("username") String username, @Body User body);

    /**
     *
     * this method purpose is to update the user password, it will check if the new password
     * matchs with the old password, if it does, the method returns a error message.
     *
     * @param username user's username
     * @param password hashmap object that must contain
     * two elements: ("dldPassword",<old user's password>),("newPassword",<new user's password>)
     * @return standard json message with information about the request(status code, error message, etc)
     * that will be serialize to a object of type StdResponse
     */

    @PATCH("user/update/password/{username}")
    Call<StdResponse> updatePassword(@Path("username") String username, @QueryMap Map<String, String> password);

    /**
     *
     * will send a mail to the user with the purpose to reset his or her
     * password.
     *
     * @param username User's username
     * @return standard json message with information about the request(status code, error message, etc)
     * that will be serialize to a object of type StdResponse
     */

    @GET("user/update/request/changepassword/{username}")
    Call<StdResponse> requestUpdatePassword(@Path("username") String username);

}