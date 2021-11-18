package com.with.us.services.auxiliary;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.with.us.LoginActivity;
import com.with.us.models.Test;
import com.with.us.models.UserInfo;
import com.with.us.services.RetrofitService;
import com.with.us.services.api.TestService;
import com.with.us.services.api.UserService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RequestHelper<T> {
    String tag;
    Activity activity;

    public RequestHelper(Activity activity, final String TAG) {
        this.tag = TAG;
        this.activity = activity;
    }

    // FIXME:
    TestService testAPI = RetrofitService.getRetrofit().create(TestService.class);
    UserService userAPI = RetrofitService.getRetrofit().create(UserService.class);

    public void getRequest() {
        // FIXME: needs to be general
        testAPI.testGet().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Test>() {
                    @Override
                    public void onNext(Test test) {
                        try {
                            String message = test.title + "\n" + test.content;
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(tag, "Error", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void postRequest(UserInfo userInfo) {
        // FIXME: needs to be general
        userAPI.createUserInfo(userInfo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserInfo>() {

                    @Override
                    public void onNext(@NonNull UserInfo response) {
                        try {
                            Toast.makeText(activity, "Successful", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(tag, "Error", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
