package com.with.us.utils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ServiceHandler extends Service {

    private static final String TAG = "ServiceHandler";
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signOut();
        }
        Log.e(TAG, "앱 종료" + rootIntent);
        // SharedPreferences 값 제거
        SharedPreferences pref = getSharedPreferences(FirebaseHelper.getUserEmail(), MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

        stopSelf();
    }

}
