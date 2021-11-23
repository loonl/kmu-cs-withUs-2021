package com.with.us.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.with.us.services.auxiliary.ApiConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static Retrofit getRetrofit() {

        return new Retrofit.Builder().baseUrl(ApiConstants.TEST_FIREBASE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}