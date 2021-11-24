package com.with.us;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity {

    private TextView text_title, text_content, text_date, text_userid;
    private Button btn_commentok;
    private EditText et_comment;
    private LinearLayout layout_content, layout_comment;
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetail);

        // 컴포넌트 초기화
        text_title = findViewById(R.id.text_postdetail_title);
        text_userid = findViewById(R.id.text_postdetail_userid);
        text_date = findViewById(R.id.text_postdetail_date);

        btn_commentok = findViewById(R.id.btn_postdetail_commentok);

        et_comment = findViewById(R.id.et_postdetail_comment);

        layout_content = findViewById(R.id.layout_postdetail_content);
        layout_comment = findViewById(R.id.layout_comment);

        LayoutInflater layoutInflater = LayoutInflater.from(PostDetailActivity.this);

        // 게시글 내용 레이아웃에 넣어주는 부분
        // imageView의 경우
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setImageResource(R.drawable.ic_launcher_foreground);
        layout_content.addView(iv);

        // textView의 경우
        TextView tv = new TextView(this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText("hello world!");
        tv.setTextSize(13);
        layout_content.addView(tv);

        // 등록하기 버튼을 눌렀을 때 이벤트
        btn_commentok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 동적으로 뷰를 만들어서 Comment Layout에 넣어준다.
                // Comment Layout에 넣어줄 View 임시생성
                View tempView = layoutInflater.inflate(R.layout.item_postdetail_comments, null);

                // 넣어줄 View 안에 내용 설정 <= 서버에서 받아오시려면 이 부분 수정하시면 될 듯 합니다.
                String text = et_comment.getText().toString();
                ((TextView) tempView.findViewById(R.id.text_comment_userid)).setText("테스트닉네임");
                ((TextView) tempView.findViewById(R.id.text_comment_content)).setText(text);
                ((TextView) tempView.findViewById(R.id.text_comment_date)).setText("2021.11.23 23:43");

                // comment Layout 에 넣어주기
                layout_comment.addView(tempView);

                // 키보드 없애고, getText 지워주기
                et_comment.getText().clear();
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // 댓글 내용의 textView를 눌렀을 때 답글을 달 수 있게 해준다.
                tempView.findViewById(R.id.text_comment_content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 답글 입력 화면인 CommentReplyActivity로 보낸다.
                        Intent intent = new Intent(getApplicationContext(), CommentReplyActivity.class);
                        intent.putExtra("userid", "테스트닉네임");
                        intent.putExtra("content", text);
                        intent.putExtra("date", "2021.11.23 23:43");
                        resultLauncher.launch(intent);
                    }
                }); // end tempView setOnClicker
            }
        }); // end btn_commentok setOnClicker

        // CommentReplyActivity로부터 답글 받아왔을 때 처리하는 부분
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) { // 답글 받아왔을 때 처리하는 부분
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            String text = intent.getStringExtra("content");
                            View tempView = layoutInflater.inflate(R.layout.item_postdetail_comments, null);
                            ((TextView) tempView.findViewById(R.id.text_comment_userid)).setText("테스트닉네임");
                            ((TextView) tempView.findViewById(R.id.text_comment_content)).setText(text);
                            ((TextView) tempView.findViewById(R.id.text_comment_date)).setText("2021.11.23 23:43");
                            layout_comment.addView(tempView);
                        }
                    }
                }); // end resultLauncher
    } // end onCreate
}