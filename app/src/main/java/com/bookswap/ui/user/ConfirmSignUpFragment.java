package com.bookswap.ui.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bookswap.R;
import com.bookswap.ui.user.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmSignUpFragment extends Fragment implements View.OnClickListener {


    public ConfirmSignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final Button redirectSignup = view.findViewById(R.id.confirm_signup_button);

        redirectSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
