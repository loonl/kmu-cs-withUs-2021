package com.with.us;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.with.us.utils.FirebaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    FirebaseAuth mAuth;
    Button activity_register_btn_continue;
    EditText activity_register_et_id;
    EditText activity_register_et_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        activity_register_btn_continue = findViewById(R.id.activity_register_btn_continue);
        activity_register_et_id = findViewById(R.id.activity_register_et_id);
        activity_register_et_pw = findViewById(R.id.activity_register_et_pw);

        activity_register_btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = activity_register_et_id.getText().toString();
                String password = activity_register_et_pw.getText().toString();
                if (email.isEmpty() || password.isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
                    activity_register_et_id.getText().clear();
                    activity_register_et_pw.getText().clear();
                    Toast.makeText(RegisterActivity.this, "이메일과 비밀번호를 다시 확인해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(mAuth, email, password);
                    mAuth.signOut();
                }
            }
        });

    }

    private void signUp(FirebaseAuth mAuth, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseHelper.setAccessToken(RegisterActivity.this);
                            signUpContinue();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            activity_register_et_id.getText().clear();
                            activity_register_et_pw.getText().clear();
                            Toast.makeText(RegisterActivity.this, "이메일과 비밀번호를 다시 확인해주시기 바랍니다.", Toast.LENGTH_SHORT)
                                    .show();
                            startActivity(getIntent());
                            finish();
                            overridePendingTransition(0, 0);
                        }
                    }
                });
    }

    private void signUpContinue() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RegisterActivityFragment fragment_register = new RegisterActivityFragment();
        transaction.replace(R.id.register_frame, fragment_register);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}