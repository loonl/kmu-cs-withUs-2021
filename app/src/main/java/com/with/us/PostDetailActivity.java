package com.with.us;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.with.us.models.PostDetail;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {

    private final static String TAG = "PostDetailActivity";

    private TextView activity_postdetail_tv_title, activity_postdetail_tv_userid, activity_postdetail_tv_content, activity_postdetail_tv_date;
    private Button activity_postdetail_btn_commentok;
    private EditText activity_postdetail_et_comment;
    private LinearLayout activity_postdetail_layout_content;
    private ActivityResultLauncher<Intent> resultLauncher;
    private RecyclerView activity_postdetail_rv_comments;
    private ArrayList<ListComments> comments;
    private ListCommentsAdapter adapter_comments;
    private String UID;
    private ImageView activity_postdetail_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetail);

        Intent intent = getIntent();
        UID = intent.getStringExtra("uid");

        // 컴포넌트 초기화
        activity_postdetail_tv_title = findViewById(R.id.activity_postdetail_tv_title);
        activity_postdetail_tv_userid = findViewById(R.id.activity_postdetail_tv_userid);
        activity_postdetail_tv_date = findViewById(R.id.activity_postdetail_tv_date);
        activity_postdetail_btn_commentok = findViewById(R.id.activity_postdetail_btn_commentok);
        activity_postdetail_et_comment = findViewById(R.id.activity_postdetail_et_comment);
        activity_postdetail_layout_content = findViewById(R.id.activity_postdetail_layout_content); // 동적으로 뷰 추가해줄 게시글 layout인데 필요없으면 지우셔도 됩니다.
        activity_postdetail_tv_content = findViewById(R.id.activity_postdetail_tv_content);
        activity_postdetail_iv = findViewById(R.id.activity_postdetail_iv);
        activity_postdetail_rv_comments = findViewById(R.id.activity_postdetail_rv_comments);

        LayoutInflater layoutInflater = LayoutInflater.from(PostDetailActivity.this);

        // 댓글 리스트에 쓸 ArrayList와 Adapter 준비
        comments = new ArrayList<ListComments>();
        adapter_comments = new ListCommentsAdapter(comments);

        // recyclerView와 Adapter 연결
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activity_postdetail_rv_comments.setLayoutManager(manager); // connect LayoutManager
        activity_postdetail_rv_comments.setAdapter(adapter_comments); // connect adapter

//        getPostDetailInformation();

        // 등록하기 버튼을 눌렀을 때 이벤트
        activity_postdetail_btn_commentok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = activity_postdetail_et_comment.getText().toString();
                // array에 넣어주고 데이터 새로고침
                comments.add(new ListComments(text, "test_nickname", "2021.11.23 23:43", 0));
                adapter_comments.notifyDataSetChanged();

                // 키보드 없애고, getText 지워주기
                activity_postdetail_et_comment.getText().clear();
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        // TODO : 답글 기능(index 구분해서 답글 달 수 있게 구현 필요)

        /**
         * // 댓글 내용의 textView를 눌렀을 때 답글을 달 수 있게 해준다.
         * tempView.findViewById(R.id.text_comment_content).setOnClickListener(new
         * View.OnClickListener() {
         *
         * @Override public void onClick(View v) { // 답글 입력 화면인 CommentReplyActivity로
         *           보낸다. Intent intent = new Intent(getApplicationContext(),
         *           CommentReplyActivity.class); intent.putExtra("userid", "테스트닉네임");
         *           intent.putExtra("content", text); intent.putExtra("date",
         *           "2021.11.23 23:43"); resultLauncher.launch(intent); } }); // end
         *           tempView setOnClicker } }); // end btn_commentok setOnClicker
         *
         *           // CommentReplyActivity로부터 답글 받아왔을 때 처리하는 부분 resultLauncher =
         *           registerForActivityResult( new
         *           ActivityResultContracts.StartActivityForResult(), new
         *           ActivityResultCallback<ActivityResult>() {
         * @Override public void onActivityResult(ActivityResult result) { // 답글 받아왔을 때
         *           처리하는 부분 if (result.getResultCode() == RESULT_OK) { Intent intent =
         *           result.getData(); String text = intent.getStringExtra("content");
         *           View tempView =
         *           layoutInflater.inflate(R.layout.item_postdetail_comments, null);
         *           ((TextView)
         *           tempView.findViewById(R.id.text_comment_userid)).setText("테스트닉네임");
         *           ((TextView)
         *           tempView.findViewById(R.id.text_comment_content)).setText(text);
         *           ((TextView)
         *           tempView.findViewById(R.id.text_comment_date)).setText("2021.11.23
         *           23:43"); layout_comment.addView(tempView); } } }); // end
         *           resultLauncher
         */
    }

//    private void getPostDetailInformation() {
//        RequestHelper.getPostAPI()
//                .getPostDetail("Bearer " + FirebaseHelper.getAccessToken(PostDetailActivity.this), UID)
//                .enqueue(new Callback<PostDetail>() {
//                    @Override
//                    public void onResponse(Call<PostDetail> call, Response<PostDetail> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(PostDetailActivity.this, response.body().title, Toast.LENGTH_SHORT).show();
//                            ctivity_postdetail_tv_title.setText(response.body().title);
//                            activity_postdetail_tv_userid.setText(response.body().displayName);
//                            activity_postdetail_tv_content.setText(response.body().content);
//                            if (!response.body().postImage.equals("None")) {
//                                Picasso.get().load(Uri.parse(response.body().postImage)).fit().networkPolicy(NetworkPolicy.OFFLINE).into(activity_postdetail_iv);
//                                activity_postdetail_iv.setVisibility(View.VISIBLE);
//                            }
//                            // TODO: 서버에 날짜 추가해야 됨
//                            // activity_postdetail_tv_date.setText(response.body().createdAt);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<PostDetail> call, Throwable t) {
//                        Log.e(TAG, t.getMessage());
//                    }
//
//                });
//        activity_postdetail_layout_content.addView(activity_postdetail_tv_content);
}
