package com.bookswap.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bookswap.R;
import com.bookswap.api.config.APIClient;
import com.bookswap.model.Address;
import com.bookswap.model.Campus;
import com.bookswap.model.Role;
import com.bookswap.model.StdResponse;
import com.bookswap.model.user.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {
    private static HttpURLConnection con;


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
        String confirm = editTextConfirm.getText().toString().trim();

        //validate
        /*if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        if (!password.equals(confirm)) {
            editTextConfirm.setError("Passwords must match");
            editTextConfirm.requestFocus();
            return;
        }*/
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);


        String url = "http://myvmlab.senecacollege.ca:6510/bookswap-0.0.1/user/signup";

       /* try{
            URL myurl = new URL(url);

            //set request method header
            con.setRequestMethod("POST");

            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Basic");

            StringBuilder content;
            try (BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()))){
                String line;
                content = new StringBuilder();

                while((line = in.readLine()) != null){
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            // System.out.println(content.toString());

            Toast.makeText(CreateAccountActivity.this, content.toString(), Toast.LENGTH_LONG).show();
        } finally */

            //user registration using api call
            //Call<StdResponse> call = APIClient.getInstance().getUserService().signup(email,username,password);

        Campus campus = new Campus();
    	campus.setName("");
        List<Role> roles = new ArrayList<>();
    	roles.add(new Role(""));
    	roles.add(new Role(""));

    	List<Address> address = new ArrayList<>();
    	address.add(new Address("", "", "", "", "",
    					"",""));

    	User user2 = new User("ryan", "marzec", "marzecreyan@gmail.com", "appMarss1", "ryan123",
    			roles,campus,address);

        try {
            APIClient api = new APIClient().getInstance();
            Call<StdResponse> call = api.getUserService().signup(user2);
            call.enqueue(new Callback<StdResponse>() {
                @Override
                public void onResponse(Call<StdResponse> call, Response<StdResponse> response){
                   // onNetworkRequestFinished();
                    /*if (response.isSuccessful()){
                        int retStatus = response.code();
                        //Toast.makeText(CreateAccountActivity.this, "success", Toast.LENGTH_LONG).show();
                        Toast.makeText(CreateAccountActivity.this, Integer.toString(retStatus), Toast.LENGTH_LONG).show();
                    } else {
                        int retStatus = response.code();
                        Toast.makeText(CreateAccountActivity.this, Integer.toString(retStatus), Toast.LENGTH_LONG).show();
                        //Toast.makeText(CreateAccountActivity.this, "fail", Toast.LENGTH_LONG).show();
                    }*/
                    if(!response.isSuccessful()){
                        try{
                            int retStatus = response.code();
                            Toast.makeText(CreateAccountActivity.this, Integer.toString(retStatus), Toast.LENGTH_LONG).show();
                            String responseX ="";
                            responseX = response.errorBody().string();
                            Log.d("TEST1",responseX);
                        }catch (IOException e){
                            Log.e("Booking Presenter", "Exception");
                        }
                    }
                }

                @Override
                public void onFailure(Call<StdResponse> call, Throwable t) {
                    Toast.makeText(CreateAccountActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(CreateAccountActivity.this, "invoking onFailure", Toast.LENGTH_LONG).show();

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
