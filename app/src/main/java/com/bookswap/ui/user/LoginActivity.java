package com.bookswap.ui.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;

import com.bookswap.api.config.APIClient;
import com.bookswap.model.StdResponse;
import com.bookswap.model.user.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.bookswap.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login_button);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Pressed", Toast.LENGTH_LONG ).show();

                final String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                final LoginRequest loginRequest = new LoginRequest(username, password);

                //add validations?

                //execute login process
                Call<StdResponse> call = new APIClient().getUserService().login(loginRequest);
                call.enqueue(new Callback<StdResponse>() {
                    @Override
                    public void onResponse(Call<StdResponse> call, Response<StdResponse> response) {
                        //Toast.makeText(LoginActivity.this, loginRequest.getUsername(), Toast.LENGTH_LONG ).show();
                        //Toast.makeText(LoginActivity.this, StdResponse, Toast.LENGTH_LONG ).show();

                    }

                    @Override
                    public void onFailure(Call<StdResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG ).show();

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
}
