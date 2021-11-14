package com.with.us;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception e) {
                } finally {
                    Intent i = new Intent(InitActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
    }
}
