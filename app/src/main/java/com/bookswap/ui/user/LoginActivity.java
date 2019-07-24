package com.bookswap.ui.user;

import android.os.Bundle;
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
import com.bookswap.UserAuth;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.bookswap.R;
import com.bookswap.ui.MainActivity;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    public static String token = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //UserAuth userAuth = (UserAuth) getApplicationContext();
        //userAuth.setAuthToken("testing");

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
        //final LoginRequest loginRequest = new LoginRequest(username, password);

        HashMap<String, String> loginHash = new HashMap<>();
        loginHash.put("username",username);
        loginHash.put("password", password);

        //this Call will query the database and check if the provided username and password is correct.
        // if the correct info is sent the sever will respond with 200 and an authentication key in the header
        try {
            Call<StdResponse> call = new APIClient().getUserService().login(loginHash);
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
                            Log.d("LOGIN1",retError);

                        }
                        else {
                            if(retCode == 200) {
                                //String display_message = "Logged in as: " + username;
                                //Toast.makeText(LoginActivity.this, display_message, Toast.LENGTH_LONG).show();

                                Headers headerList = response.headers();
                                String syr = headerList.get("Authorization");
                                int temp = syr.indexOf(" ");
                                token = syr.substring(temp, syr.length());
                                Log.i("TOKEN", token);

                                UserAuth userAuth = (UserAuth) getApplicationContext();
                                userAuth.setAuthToken(token);
                                userAuth.setUsername(username);

                                //if successful login, get the user data
                                getUserData(username, token);

                                if(token != null){
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<StdResponse> call, Throwable t) {
                    //Log.e("LOGIN_FAILURE", t.toString());
                    Toast.makeText(LoginActivity.this, "Failed to log in", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getUserData(String user, String token) {
        try{
            Call<HashMap<String, Object>> cal2 = new APIClient().getUserService().findUserByUsername(user, token);
            cal2.enqueue(new Callback<HashMap<String, Object>>(){

                @Override
                public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                    int retCode = response.code();
                    String retMessage = response.message();

                    HashMap<String,Object> hashReponse = response.body();

                    //Toast.makeText(LoginActivity.this, email, Toast.LENGTH_LONG).show();
                    Log.d("USER_INFO",hashReponse.get("user").toString());
                    if(hashReponse.containsKey("file")) {
                        Log.d("USER_PHOTO", hashReponse.get("file").toString());
                    }
                    //Log.d("USERDATA_FAILURE1", response.errorBody().toString());

                }

                @Override
                public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                    //Toast.makeText(LoginActivity.this, "Failed to load user data", Toast.LENGTH_LONG ).show();
                    Log.d("USERDATA_FAILURE2", String.valueOf(t));
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