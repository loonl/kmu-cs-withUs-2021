package com.with.us.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.with.us.services.auxiliary.ApiConstants;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    // private static OkHttpClient mOkHttpClient;
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        // RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        Gson gson = new GsonBuilder().setLenient().create();

        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(ApiConstants.FIREBASE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(rxAdapter);
            retrofit = builder.build();
        }

        return retrofit;

    }
//     public static UserService getUserAPI() {
//     return createAPI(UserService.class, ApiConstants.FIREBASE_BASE_URL);
//     }
//
//     private static <T> T createAPI(Class<T> serviceClass, String baseUrl) {
//     Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
//     // RxJava2CallAdapterFactory rxAdapter =
//     RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
//     // Retrofit retrofit = new
//     Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
//     // .addCallAdapterFactory(rxAdapter).build();
//     return retrofit.create(serviceClass);
//     }
}
