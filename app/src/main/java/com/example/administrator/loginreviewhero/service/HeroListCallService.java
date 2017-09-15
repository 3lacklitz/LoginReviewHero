package com.example.administrator.loginreviewhero.service;

import com.example.administrator.loginreviewhero.model.HeroList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HeroListCallService {
    @GET("test-mobile/iOS/json/test2.json")
    Call<HeroList> getHeroList();
}
