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
                try{
                    userSignIn();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void userSignIn() throws Exception{

        //variables to hold username and password
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);

        final String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        final LoginRequest loginRequest = new LoginRequest(username, password);

        //this Call will query the database and check if the provided username and password is correct.
        // if the correct info is sent the sever will respond with 200 and an authentication key in the header
        try {
            Call<StdResponse> call = new APIClient().getUserService().login(loginRequest);
            call.enqueue(new Callback<StdResponse>() {
                @Override
                public void onResponse(Call<StdResponse> call, Response<StdResponse> response) {
                    try{
                        //if response fails catch error and provide a simple failed login message
                        int retCode = response.code();
                        String retMessage = response.message();

                        if(!response.isSuccessful()){
                                String retError = String.valueOf(response.errorBody());
                                Toast.makeText(LoginActivity.this, retError, Toast.LENGTH_LONG).show();
                                Log.d("LOGIN",retError);

                        }
                        else {
                            if(retCode == 200) {
                                Toast.makeText(LoginActivity.this, retMessage, Toast.LENGTH_LONG).show();

                                Headers headerList = response.headers();
                                String syr = headerList.get("Authorization");
                                int temp = syr.indexOf(" ");
                                token = syr.substring(temp, syr.length());
                                Log.i("TOKEN", token);

                                //if successful login, get the user data
                                getUserData(username, token);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<StdResponse> call, Throwable t) {
                    Log.e("LOGIN_FAILURE", t.toString());
                    Toast.makeText(LoginActivity.this, "Failed to log in", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getUserData(String user, String token) {
        try{
            Call<User> cal2 = new APIClient().getUserService().findUserByUsername(user);
            cal2.enqueue(new Callback<User>(){

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    int retCode = response.code();
                    String retMessage = response.message();

                    if(!response.isSuccessful()){
                        String retError = String.valueOf(response.errorBody());
                        Toast.makeText(LoginActivity.this, retError, Toast.LENGTH_LONG).show();
                        Log.d("USERDATA",retError);

                    }
                    else{
                        Toast.makeText(LoginActivity.this, retMessage, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Failed to load user data", Toast.LENGTH_LONG ).show();
                    Log.d("USERDATA_FAILURE", String.valueOf(t));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
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
}
