package com.example.gross.mobileclient.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gross on 17.01.2017.
 */

public class RegistrationResponse {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("token")
    @Expose
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
