package com.with.us;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.with.us.utils.ServiceHandler;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        startService(new Intent(this, ServiceHandler.class));
        TextView tv=findViewById(R.id.mainTitle);
        ImageView iv=findViewById(R.id.mainLogo);

        iv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.bounce));
        tv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.linear));

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);
                } catch (Exception e) {
                } finally {
                    Intent i = new Intent(InitActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.decelerate_out,R.anim.decelerate_in);
                    finish();
                }
            }
        };
        thread.start();
    }
}
