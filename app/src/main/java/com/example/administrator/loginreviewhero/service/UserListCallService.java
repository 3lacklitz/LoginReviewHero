package com.example.administrator.loginreviewhero.service;

import com.example.administrator.loginreviewhero.model.UserList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserListCallService {
    @GET("select_member.php")
    Call<UserList> getUserDetail();
}
