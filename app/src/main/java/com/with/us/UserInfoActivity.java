package com.with.us;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.with.us.models.Test;
import com.with.us.models.UserInfo;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";

    TextView activity_user_info_tv_change;
    Button activity_user_info_btn_logout, activity_user_info_btn_quit;
    ImageView activity_user_info_btn_back;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        activity_user_info_tv_change = findViewById(R.id.activity_user_info_tv_change);
        activity_user_info_btn_logout = findViewById(R.id.activity_user_info_btn_logout);
        activity_user_info_btn_quit = findViewById(R.id.activity_user_info_btn_quit);
        activity_user_info_btn_back = findViewById(R.id.activity_user_info_btn_back);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        activity_user_info_tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_info_change_intent = new Intent(UserInfoActivity.this, UserInfoChangeActivity.class);
                startActivity(user_info_change_intent);
            }
        });

        // 창닫기 눌렀을 때
        activity_user_info_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 로그아웃 버튼 눌렀을때
        activity_user_info_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserInfoActivity.this).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(UserInfoActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                Intent intent = new Intent();
                                intent.setClass(UserInfoActivity.this, LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }).setNegativeButton("취소", null).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });

        // 회원탈퇴 버튼 눌렀을때
        activity_user_info_btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserInfoActivity.this).setTitle("회원탈퇴").setMessage("회원을 탈퇴 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                RequestHelper.getUserAPI()
                                        .deleteUserInfo(
                                                "Bearer " + FirebaseHelper.getAccessToken(UserInfoActivity.this))
                                        .enqueue(new Callback<UserInfo>() {
                                            @Override
                                            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                                mAuth.signOut();
                                                Toast.makeText(UserInfoActivity.this, "계정을 삭제하였습니다.",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent();
                                                intent.setClass(UserInfoActivity.this, LoginActivity.class)
                                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onFailure(Call<UserInfo> call, Throwable t) {

                                            }
                                        });
                            }
                        }).setNegativeButton("취소", null).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });

        if (user != null) {
            getUserData();
        }

    }

    private void getUserData() {
        TextView userName = findViewById(R.id.activity_user_info_tv_name);
        TextView birthDate = findViewById(R.id.activity_user_info_tv_birth);
        TextView region = findViewById(R.id.activity_user_info_tv_region);

        RequestHelper.getUserAPI().getUserInfo("Bearer " + FirebaseHelper.getAccessToken(this))
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.isSuccessful()) {
                            userName.setText(response.body().displayName);
                            birthDate.setText(String.valueOf(response.body().birthDate));
                            region.setText(response.body().region);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }
}
