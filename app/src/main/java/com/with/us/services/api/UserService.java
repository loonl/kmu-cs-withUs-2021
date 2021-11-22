package com.with.us.services.api;

import com.with.us.models.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @GET("user/get")
    Call<UserInfo> getUserInfo(@Header("Authorization") String auth);

    @POST("user/create")
    Call<UserInfo> createUserInfo(@Header("Authorization") String auth, @Body UserInfo userInfo);

    @POST("user/modify")
    Call<UserInfo> modifyUserInfo(@Header("Authorization") String auth, @Body UserInfo userInfo);

    @GET("user/delete")
    Call<UserInfo> deleteUserInfo(@Header("Authorization") String auth);
}
