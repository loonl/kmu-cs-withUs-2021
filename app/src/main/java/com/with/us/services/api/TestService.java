package com.with.us.services.api;

import com.with.us.models.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TestService {
    @GET("test")
    Call<Test> testGet();

    @POST("test")
    Call<Test> testPost(@Body Test test);
}
