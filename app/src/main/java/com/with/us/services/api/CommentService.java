package com.with.us.services.api;

import com.with.us.models.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CommentService {
    @GET("post/get")
    Call<List<Comment>> getComment(@Header("Authorization") String auth);

    @POST("post/create")
    Call<List<Comment>> createComment(@Header("Authorization") String auth, @Body Comment comment);

    @POST("post/modify")
    Call<List<Comment>> modifyComment(@Header("Authorization") String auth, @Body Comment comment);

    @GET("post/delete")
    Call<List<Comment>> deleteComment(@Header("Authorization") String auth);
}
