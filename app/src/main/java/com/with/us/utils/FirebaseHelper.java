package com.with.us.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class FirebaseHelper {

    private static final String TAG = "FirebaseHelper";
    public static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static void setAccessToken(Context context) {

        if (user != null) {
            user.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult result) {
                    String PREF_ID_TOKEN = result.getToken();
                    Log.d(TAG, PREF_ID_TOKEN);
                    SharedPreferences preferences = context.getSharedPreferences(user.getEmail(), 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("token", PREF_ID_TOKEN);
                    editor.apply();
                }
            });
        }
    }

    public static String getAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(user.getEmail(), 0);
        return preferences.getString("token", "");
    }

}
