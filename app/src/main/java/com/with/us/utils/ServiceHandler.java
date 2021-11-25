package com.with.us.utils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServiceHandler extends Service {

    private static final String TAG = "ServiceHandler";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e(TAG, "앱 종료" + rootIntent);
        // SharedPreferences 값 제거
        SharedPreferences pref = getSharedPreferences(FirebaseHelper.getUserEmail(), MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        stopSelf();
    }

}
