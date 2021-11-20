package com.with.us;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInfoChange extends AppCompatActivity {

    EditText activity_user_info_change_et_pw,activity_user_info_change_et_name,activity_user_info_change_et_birth;
    RadioGroup activity_user_info_change_rg;
    Spinner activity_user_info_change_spn_region, activity_user_info_change_spn_interest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);

        activity_user_info_change_et_pw = findViewById(R.id.activity_user_info_change_et_pw);
        activity_user_info_change_et_name = findViewById(R.id.activity_user_info_change_et_name);
        activity_user_info_change_et_birth = findViewById(R.id.activity_user_info_change_et_birth);
        activity_user_info_change_rg = findViewById(R.id.activity_user_info_change_rg);
        activity_user_info_change_spn_region = (Spinner) findViewById(R.id.activity_user_info_change_spn_region);
        activity_user_info_change_spn_interest = (Spinner) findViewById(R.id.activity_user_info_change_spn_interest);

    }
}
