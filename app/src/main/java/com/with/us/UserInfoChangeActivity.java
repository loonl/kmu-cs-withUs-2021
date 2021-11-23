package com.with.us;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.with.us.models.UserInfo;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoChangeActivity extends AppCompatActivity {

    EditText activity_user_info_change_et_pw, activity_user_info_change_et_name, activity_user_info_change_et_birth;
    RadioGroup activity_user_info_change_rg;
    Spinner activity_user_info_change_spn_region, activity_user_info_change_spn_interest;
    Button activity_user_info_change_fragment_btn;

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
        activity_user_info_change_fragment_btn = findViewById(R.id.activity_user_info_change_fragment_btn);

        activity_user_info_change_fragment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserInfoChangeActivity.this).setTitle("회원정보 변경").setMessage("회원정보를 수정하시겠습니까?").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String password = activity_user_info_change_et_pw.getText().toString();
                        String displayName = activity_user_info_change_et_name.getText().toString();
                        int birthDate = Integer.parseInt(activity_user_info_change_et_birth.getText().toString());
                        String region = String.valueOf(activity_user_info_change_spn_region.getSelectedItem());
                        String interest = String.valueOf(activity_user_info_change_spn_interest.getSelectedItem());

                        UserInfo userInfo = new UserInfo(displayName, birthDate, "", region, interest);
                        RequestHelper.getUserAPI().modifyUserInfo("Bearer " + FirebaseHelper.getAccessToken(UserInfoChangeActivity.this), userInfo).enqueue(new Callback<UserInfo>() {
                            @Override
                            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                Toast.makeText(UserInfoChangeActivity.this, "회원정보를 변경하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<UserInfo> call, Throwable t) {

                            }
                        });
                    }
                }).setNegativeButton(android.R.string.cancel, null).setIcon(android.R.drawable.ic_dialog_info).show();
            }
        });


    }
}
