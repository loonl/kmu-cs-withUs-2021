package com.with.us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView register_tv  = (TextView) findViewById(R.id.activity_login_tv_register);
        register_tv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent register_intent  = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(register_intent);
            }
        });
    }
}

