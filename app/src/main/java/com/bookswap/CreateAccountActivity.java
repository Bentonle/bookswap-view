package com.bookswap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bookswap.ui.login.LoginActivity;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    //handle clickable text
    public void textClickables(View view) {
        Intent intent;

        int id = view.getId();

        if(id == R.id.click_for_sign_in_page) {
            intent =  new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
