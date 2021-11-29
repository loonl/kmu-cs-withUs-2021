package com.with.us;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.with.us.models.PostDetail;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {

    private final static String TAG = "PostDetailActivity";

    private TextView activity_postdetail_tv_title, activity_postdetail_tv_userid,
            activity_postdetail_tv_content, activity_postdetail_tv_date;
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

//        // recyclerView 속 텍스트 클릭 이벤트 구현
//        adapter_comments.setOnItemClickListener(new ListCommentsAdapter.OnItemClickListener() {
//
//            @Override
//            public void onCommentDeleteClick(View v, int position) {
//                // 마지막 index 값 삭제할 때 아래 코드로 가면 에러 발생
//                if (position == comments.size() - 1) {
//                    comments.remove(position);
//                    Toast.makeText(getApplicationContext(), "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                    adapter_comments.notifyDataSetChanged();
//                    return;
//                }
//                // 답글이 아래 달려있는 댓글이면 다르게 설정
//                if (comments.get(position + 1).getViewType() == ListCommentsViewType.REPLY) {
//                    comments.remove(position);
//                    comments.add(position, new ListComments("삭제된 댓글입니다.", "", "",
//                            ListCommentsViewType.COMMENT));
//                    adapter_comments.notifyItemChanged(position); // 삭제 버튼이 바로 안사라지는 버그
//                } else {
//                    comments.remove(position);
//                }
//                Toast.makeText(getApplicationContext(), "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                adapter_comments.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCommentReplyClick(View v, int position) {
//                String text = ((TextView) v).getText().toString();
//                Intent intent = new Intent(getApplicationContext(),
//                        CommentReplyActivity.class);
//                intent.putExtra("userid", comments.get(position).getUserId());
//                intent.putExtra("content", comments.get(position).getContent());
//                intent.putExtra("time", comments.get(position).getTime());
//                intent.putExtra("index", position);
//                resultLauncher.launch(intent);
//            }
//
//            @Override
//            public void onCommentReplyDeleteClick(View v, int position) {
//
//                // 맨 마지막 index의 단 하나뿐인 답글인 경우 밑으로 가면 에러 발생
//                if (position == comments.size() - 1) {
//                    comments.remove(position);
//                    comments.remove(position - 1);
//                    Toast.makeText(getApplicationContext(), "답글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                    adapter_comments.notifyDataSetChanged();
//                    return;
//                }
//
//                // 삭제된 댓글의 단 하나인 답글이었을 때
//                if (comments.get(position - 1).getContent().equals("삭제된 댓글입니다.") &&
//                        (comments.get(position + 1).getViewType() == ListCommentsViewType.COMMENT)) {
//                    comments.remove(position);
//                    comments.remove(position - 1);
//                } else
//                    comments.remove(position);
//                Toast.makeText(getApplicationContext(), "답글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                adapter_comments.notifyDataSetChanged();
//            }
//        });

        // recyclerView와 Adapter 연결
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activity_postdetail_rv_comments.setLayoutManager(manager); // connect LayoutManager
        activity_postdetail_rv_comments.setAdapter(adapter_comments); // connect adapter

        getPostDetailInformation();

        // 댓글 등록하기 버튼을 눌렀을 때 이벤트
        activity_postdetail_btn_commentok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = activity_postdetail_et_comment.getText().toString();


//                // array에 넣어주고 데이터 새로고침
//                comments.add(new ListComments(text, "test_nickname", "2021.11.23 23:43",
//                        ListCommentsViewType.COMMENT));
//                adapter_comments.notifyDataSetChanged();


                // 키보드 없애고, getText 지워주기
                activity_postdetail_et_comment.getText().clear();
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
//
//        // 답글화면에서 넘어왔을 때 처리하는 resultLauncher 부분
//        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//        new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK) {
//                    Intent intent = result.getData();
//                    String userid = intent.getStringExtra("userid");
//                    String time = intent.getStringExtra("time");
//                    String content = intent.getStringExtra("content");
//                    int index = intent.getIntExtra("index", 0);
//
//                    // 마지막 index 댓글의 답글이면 아래 코드에서 에러가 발생
//                    if (index == comments.size()) {
//                        comments.add(index, new ListComments(content, userid, time,
//                                ListCommentsViewType.REPLY));
//                        adapter_comments.notifyDataSetChanged();
//                        return;
//                    }
//
//                    // 답글이 여러개면 가장 밑에 달려야하기 때문에 달아야 할 index 추적
//                    while (true) {
//                        if (index == comments.size())
//                            break;
//                        if (comments.get(index).getViewType() == ListCommentsViewType.COMMENT)
//                            break;
//                        index++;
//                    }
//
//                    // array 에 답글 넣어주고 새로고침
//                    comments.add(index, new ListComments(content, userid, time,
//                            ListCommentsViewType.REPLY));
//                    adapter_comments.notifyDataSetChanged();
//                }
//            } // end on ActivityResult
//        }); // end ResultLauncher
//
//        // TODO: 로그인된 유저가 쓴 댓글 및 답글에만 "삭제" TEXTVIEW가 나타날 수 있게 구현하면 좋을 듯합니다?

    }

    private void getPostDetailInformation() {
        RequestHelper.getPostAPI().getPostDetail("Bearer " + FirebaseHelper.getAccessToken(this), UID).enqueue(new Callback<PostDetail>() {
            @Override
            public void onResponse(Call<PostDetail> call, Response<PostDetail> response) {
                if (response.isSuccessful()) {
                    activity_postdetail_tv_title.setText(response.body().title);
                    activity_postdetail_tv_userid.setText(response.body().displayName);
                    activity_postdetail_tv_content.setText(response.body().content);
                    DateFormat dateFormat = new SimpleDateFormat("MM-dd");
                    String createdAt = dateFormat.format(new Date(Long.parseLong(response.body().createdAt)));

                    activity_postdetail_tv_date.setText(createdAt);
                    if (!response.body().postImage.equals("None")) {
                        activity_postdetail_iv.setVisibility(View.VISIBLE);
                        Glide.with(PostDetailActivity.this).load(response.body().postImage).centerCrop().placeholder(R.drawable.no_image).into(activity_postdetail_iv);
                    }
                }
            }

            @Override
            public void onFailure(Call<PostDetail> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }

        });
    }
}
