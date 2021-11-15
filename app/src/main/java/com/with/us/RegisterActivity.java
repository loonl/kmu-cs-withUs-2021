package com.with.us;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class RegisterActivity extends AppCompatActivity {

    Button activity_register_btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        activity_register_btn_continue = (Button)findViewById(R.id.activity_register_btn_continue);


        activity_register_btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //activity_register_btn_continue 눌렀을때 아이디중복, 비밀번호 조건 확인 필요 코드 작성해야함 -> 조건 만족시 fragment 실행
                //
                //

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                RegisterActivityFragment fragment_register = new RegisterActivityFragment();
                transaction.replace(R.id.register_frame,fragment_register);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



    }
}