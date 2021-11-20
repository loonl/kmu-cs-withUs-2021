package com.with.us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInfo extends AppCompatActivity {

    TextView activity_user_info_tv_change;
    Button activity_user_info_btn_logout,activity_user_info_btn_quit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        activity_user_info_tv_change = findViewById(R.id.activity_user_info_tv_change);
        activity_user_info_btn_logout = findViewById(R.id.activity_user_info_btn_logout);
        activity_user_info_btn_quit = findViewById(R.id.activity_user_info_btn_quit);

        activity_user_info_tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_info_change_intent = new Intent(UserInfo.this, UserInfoChange.class);
                startActivity(user_info_change_intent);
            }
        });

        //로그아웃 버튼 눌렀을때
        activity_user_info_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //회원탈퇴 버튼 눌렀을때
        activity_user_info_btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
