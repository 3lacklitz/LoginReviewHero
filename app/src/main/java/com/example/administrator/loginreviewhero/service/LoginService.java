package com.example.administrator.loginreviewhero.service;

import com.example.administrator.loginreviewhero.model.GenericStatus;
import com.example.administrator.loginreviewhero.model.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> getLoginData(@Field("username") String usernameString,
                             @Field("password") String passwordString);

    @FormUrlEncoded
    @POST("insert_member.php")
    Call<GenericStatus> getSignUpData (@Field("member_username") String insertUsername,
                                       @Field("member_password") String insertPassword,
                                       @Field("member_name") String insertName,
                                       @Field("member_email") String insertEmail,
                                       @Field("member_tel") String insertTel,
                                       @Field("member_image") String insertImage);

    @FormUrlEncoded
    @POST("update_member.php")
    Call<GenericStatus> getEditData(@Field("member_id") String updateMemberId,
                                    @Field("member_name") String updateName,
                                    @Field("member_email") String updateMail,
                                    @Field("member_tel") String updateTel);
}
