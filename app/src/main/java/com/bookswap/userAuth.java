package com.bookswap;

import android.app.Application;

public class userAuth extends Application {
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
