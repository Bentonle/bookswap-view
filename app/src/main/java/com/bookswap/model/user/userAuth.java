package com.bookswap.model.user;

import android.app.Application;

public class userAuth  {
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
