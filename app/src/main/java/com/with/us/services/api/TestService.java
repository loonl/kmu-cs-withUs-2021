package com.with.us.services.api;

import com.with.us.models.Test;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TestService {
    @GET("test")
    Observable<Test> testGet();

    @POST("test")
    Observable<Test> testPost(@Body Test test);
}
