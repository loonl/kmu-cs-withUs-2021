package com.with.us;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.with.us.models.PostDetail;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFormActivity extends AppCompatActivity {

    private static final String TAG = "PostFormActivity";
    Button activity_post_form_btn;
    EditText activity_post_form_title, activity_post_form_content;
    String title, content, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);

        activity_post_form_title = findViewById(R.id.activity_post_form_title);
        activity_post_form_content = findViewById(R.id.activity_post_form_content);
        activity_post_form_btn = findViewById(R.id.activity_post_form_btn);
        title = activity_post_form_title.getText().toString();
        content = activity_post_form_content.getText().toString();
        category = getIntent().getStringExtra("category");

        activity_post_form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetail postDetail = new PostDetail(FirebaseHelper.user.getUid(), category,
                        FirebaseHelper.user.getDisplayName(), title, content, 0, 0, "None");

                RequestHelper.getPostAPI()
                        .createPostDetail("Bearer " + FirebaseHelper.getAccessToken(PostFormActivity.this), postDetail)
                        .enqueue(new Callback<List<PostDetail>>() {
                            @Override
                            public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(PostFormActivity.this, "게시글을 작성하였습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<PostDetail>> call, Throwable t) {

                            }
                        });
            }
        });

    }
}
