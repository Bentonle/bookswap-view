package com.bookswap.ui.user;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;

import com.bookswap.api.config.APIClient;
import com.bookswap.model.StdResponse;
import com.bookswap.model.user.LoginRequest;
import com.bookswap.model.user.User;
import com.bookswap.ui.HomeFragment;
import com.bookswap.userAuth;

import okhttp3.Headers;
import okhttp3.internal.http2.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.bookswap.R;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //variables to hold username and password
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login_button);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        //login button
        //
        // 1: Check if credetials are valide
        // 2: if valide, will request the users information
        // 3: populate user class with user data
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Pressed", Toast.LENGTH_LONG ).show();

                final String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                final LoginRequest loginRequest = new LoginRequest(username, password);

                //add validations?

                //execute login process

                //test for correct login credentials
                //
                //this Call will query the database and check if the provided username and password is correct.
                // if the correct info is sent the sever will respond with 200 and an authentication key in the header
                //
                Call<StdResponse> call = new APIClient().getUserService().login(loginRequest);
                call.enqueue(new Callback<StdResponse>() {
                    @Override
                    public void onResponse(Call<StdResponse> call, Response<StdResponse> response) {

                        //if response fails catch error and provide a simple failed login message
                        if(!response.isSuccessful()){
                            try{
                                int retStatus = response.code();
                                Toast.makeText(LoginActivity.this, Integer.toString(retStatus), Toast.LENGTH_LONG).show();
                                String responseX ="";
                                responseX = response.errorBody().string();
                                Log.d("LOGIN",responseX);
                            }catch (IOException e){
                                Log.e("LOGIN", "failed to login");
                                Toast.makeText(LoginActivity.this, "test", Toast.LENGTH_LONG).show();
                            }

                        // if the response is successful we will grab the authentication and save it to a global token
                        // to be used for other requests that require a user to be logged in.
                        //this token will be used in APICLient.java in the client interceptor
                        }else {
                            String login_message = "logged in as: " + username;
                            Toast.makeText(LoginActivity.this, "logged in as buttchecks", Toast.LENGTH_LONG).show();
                            Headers headerList = response.headers();
                            String syr = headerList.get("Authorization");
                            int temp = syr.indexOf(" ");
                            token = syr.substring(temp, syr.length());
                            Log.d("HEADERS", token);

                            //once response code 200 in received we will query the database for the user information
                            // this call will include the username and the authentication token recieved from the validate user call above
                            Call<User> call2 = new APIClient().getUserService().findUserByUsername(username);
                            call2.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {

                                    //catch error if response fails
                                    if(!response.isSuccessful()){
                                        try{
                                            int retStatus = response.code();
                                            Toast.makeText(LoginActivity.this, Integer.toString(retStatus), Toast.LENGTH_LONG).show();
                                            String responseX ="";
                                            responseX = response.errorBody().string();
                                            Log.d("LOGIN_DATA",responseX);
                                        }catch (IOException error){
                                            Log.e("LOGIN_DATA", String.valueOf(error));
                                        }
                                    }else{
                                        //if the call is successfull
                                        //Toast.makeText(LoginActivity.this, "potato", Toast.LENGTH_LONG).show();
                                        //int retStatus = response.code();
                                        String ret = response.message();
                                        Toast.makeText(LoginActivity.this, ret, Toast.LENGTH_LONG).show();

                                        //redirect to `homepage on successful login
                                        /*android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.flMain, new HomeFragment());
                                        ft.commit();*/



                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "Failed to load user data", Toast.LENGTH_LONG ).show();
                                    Log.e("OnFailure", String.valueOf(t));

                                }
                            });
                            String msg = response.body().getMessage();
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();

                            Log.e ("LOGIN", msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<StdResponse> call, Throwable t) {
                        toastMessage("Failed login request");
                    }
                });
            }
        });
    }


    //handle clickable text
    public void textClickables(View view) {
        Intent intent;

        int id = view.getId();

        if(id == R.id.clcikHere1) {
            intent =  new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.clickHere2) {
            intent =  new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        }
    }
    private void toastMessage(String message){ Toast.makeText(this, message, Toast.LENGTH_LONG).show(); }
}
