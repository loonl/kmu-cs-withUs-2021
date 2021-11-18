package com.with.us.services.api;

import com.with.us.models.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
    @GET("user/get")
    Observable<UserInfo> getUserInfo();

    @POST("user/create")
    Observable<UserInfo> createUserInfo(@Body UserInfo userInfo);

    @POST("user/modify")
    Observable<UserInfo> modifyUserInfo(@Body UserInfo userInfo);

    @GET("user/delete")
    Observable<UserInfo> deleteUserInfo();
}
