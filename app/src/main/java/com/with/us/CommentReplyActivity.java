package com.with.us;

import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CommentReplyActivity extends AppCompatActivity {
    private TextView activity_commentreply_tv_userid, activity_commentreply_tv_date,
            activity_commentreply_tv_content;

    private Button activity_commentreply_btn_replyok;
    private EditText activity_commentreply_et_reply;
    private String userid, date, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentreply);

        // 컴포넌트 초기화
        activity_commentreply_tv_userid = findViewById(R.id.activity_commentreply_tv_userid);
        activity_commentreply_tv_date = findViewById(R.id.activity_commentreply_tv_date);
        activity_commentreply_tv_content = findViewById(R.id.activity_commentreply_tv_content);

        activity_commentreply_btn_replyok = findViewById(R.id.activity_commentreply_btn_replyok);

        activity_commentreply_et_reply = findViewById(R.id.activity_commentreply_et_reply);

        // 값 받아온거 할당
        Intent inIntent = getIntent();
        userid = inIntent.getStringExtra("userid");
        date = inIntent.getStringExtra("date");
        content = inIntent.getStringExtra("content");

        activity_commentreply_tv_userid.setText(userid);
        activity_commentreply_tv_date.setText(date);
        activity_commentreply_tv_content.setText(content);

        // 답글 버튼 눌렀을 때 다시 PostDetail 쪽으로 보내주기
        activity_commentreply_btn_replyok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = activity_commentreply_et_reply.getText().toString();
                Intent outIntent = new Intent(getApplicationContext(), PostDetailActivity.class);
                outIntent.putExtra("content", text);
                setResult(RESULT_OK, outIntent);

                // 키보드 숨기기
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                finish();
            }
        });

    }
}
