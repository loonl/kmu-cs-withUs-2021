package com.with.us.services.auxiliary;

import com.with.us.services.RetrofitService;
import com.with.us.services.api.CommentService;
import com.with.us.services.api.PostService;
import com.with.us.services.api.TestService;
import com.with.us.services.api.UserService;

public class RequestHelper<T> {

    public static TestService getTestAPI() {
        return RetrofitService.getRetrofit().create(TestService.class);
    }

    public static UserService getUserAPI() {
        return RetrofitService.getRetrofit().create(UserService.class);
    }

    public static PostService getPostAPI() {
        return RetrofitService.getRetrofit().create(PostService.class);
    }

    public static CommentService getCommentAPI() {
        return RetrofitService.getRetrofit().create(CommentService.class);
    }

}
