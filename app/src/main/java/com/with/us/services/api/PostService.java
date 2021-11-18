package com.with.us.services.api;

import com.with.us.models.PostDetail;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostService {
    @GET("post/get")
    Observable<PostDetail> getPostDetail();

    @POST("post/create")
    Observable<PostDetail> createPostDetail(@Body PostDetail PostDetail);

    @POST("post/modify")
    Observable<PostDetail> modifyPostDetail(@Body PostDetail PostDetail);

    @GET("post/delete")
    Observable<PostDetail> deletePostDetail();
}
