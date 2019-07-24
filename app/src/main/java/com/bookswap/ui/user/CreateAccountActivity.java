package com.bookswap.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bookswap.R;
import com.bookswap.api.config.APIClient;
import com.bookswap.model.Address;
import com.bookswap.model.Campus;
import com.bookswap.model.Role;
import com.bookswap.model.StdResponse;
import com.bookswap.model.user.User;
import com.bookswap.ui.HomeFragment;
import com.bookswap.ui.MainActivity;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {
    private static HttpURLConnection con;
    Boolean signup = false;



    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        editTextUsername = findViewById(R.id.create_username);
        editTextEmail = findViewById(R.id.create_email);
        editTextPassword = findViewById(R.id.create_password);
        editTextConfirm = findViewById(R.id.create_confirm_password);
        final Button createAccountButton = findViewById(R.id.create_account_button);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //preform signup
                try {
                    userSignUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }

    private void userSignUp() throws Exception{

        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        HashMap<String,Object> user_ = new HashMap<>();

        HashMap<String,String> hashRoles = new HashMap<>();
        hashRoles.put("name","USER");
        HashMap[] roles_ = {hashRoles};

        HashMap<String,String> campus_ = new HashMap<>();
        campus_.put("name","name");

        HashMap<String,String> address_ = new HashMap<>();
        address_.put("addressLine1","addressLine1");
        address_.put("addressLine2","addressLine2");
        address_.put("addressLine3","addressLine3");
        address_.put("city","city");
        address_.put("province","province");
        address_.put("country","country");
        address_.put("postalCode","pose");

        user_.put("email",email);
        user_.put("username",username);
        user_.put("password",password);
        user_.put("roles",roles_);
        user_.put("campus",campus_);
        user_.put("address",address_);

        File file = new File("storage/emulated/0/Download/xyPtn4m_d.jpg");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        try {
            APIClient api = new APIClient().getInstance();
            Call<StdResponse> call = api.getUserService().signup(user_, null);

            call.enqueue(new Callback<StdResponse>() {
                @Override
                public void onResponse(Call<StdResponse> call, Response<StdResponse> response){
                    try{
                        int retStatus = response.code();
                        String retMessage = response.message();

                        if(retStatus == 200) {
                            DisplayMetrics dm = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(dm);

                            int width = dm.widthPixels;
                            int height = dm.heightPixels;

                            LayoutInflater inflater = (LayoutInflater) CreateAccountActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View popupLayout = inflater.inflate(R.layout.fragment_confirm_sign_up, null);

                            final PopupWindow popupWindow = new PopupWindow(popupLayout, (int)(width * 0.8), (int)(height * 0.3), true);
                            popupWindow.setFocusable(true);
                            popupWindow.showAtLocation(popupLayout, Gravity.CENTER,0,0);

                            Button redirectButton = (Button)popupLayout.findViewById(R.id.confirm_signup_button);
                            redirectButton.setOnClickListener(new Button.OnClickListener(){

                                @Override
                                public void onClick(View v) {
                                    popupWindow.dismiss();
                                    Intent signInIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                    startActivity(signInIntent);                                }
                            });

                        }

                        Toast.makeText(CreateAccountActivity.this, Integer.toString(retStatus), Toast.LENGTH_LONG).show();
                        String responseX ="";
                        responseX = response.errorBody().string();
                        Log.d("TEST1",responseX);

                    }catch (Exception e){
                        Log.e("SIGNUPFAIL", String.valueOf(e));
                    }
                }

                @Override
                public void onFailure(Call<StdResponse> call, Throwable t) {
                    Log.e("TEST1",t.toString());
                    Toast.makeText(CreateAccountActivity.this, "Failed to sign up", Toast.LENGTH_LONG).show();

                }

            });
        } catch(Throwable e){
            e.printStackTrace();
        }
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