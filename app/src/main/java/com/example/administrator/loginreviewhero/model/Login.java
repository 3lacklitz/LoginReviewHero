package com.example.administrator.loginreviewhero.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login extends GenericStatus{
    @SerializedName("user_detail")
    private List<UserDetail> userDetails;

    public List<UserDetail> getUserDetails() {
        return userDetails;
    }
}
