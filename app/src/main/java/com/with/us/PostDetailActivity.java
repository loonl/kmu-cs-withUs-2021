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

        // ???????????? ?????????
        activity_postdetail_tv_title = findViewById(R.id.activity_postdetail_tv_title);
        activity_postdetail_tv_userid = findViewById(R.id.activity_postdetail_tv_userid);
        activity_postdetail_tv_date = findViewById(R.id.activity_postdetail_tv_date);
        activity_postdetail_btn_commentok = findViewById(R.id.activity_postdetail_btn_commentok);
        activity_postdetail_et_comment = findViewById(R.id.activity_postdetail_et_comment);
        activity_postdetail_layout_content = findViewById(R.id.activity_postdetail_layout_content); // ???????????? ??? ???????????? ????????? layout?????? ??????????????? ???????????? ?????????.
        activity_postdetail_tv_content = findViewById(R.id.activity_postdetail_tv_content);
        activity_postdetail_iv = findViewById(R.id.activity_postdetail_iv);
        activity_postdetail_rv_comments = findViewById(R.id.activity_postdetail_rv_comments);

        LayoutInflater layoutInflater = LayoutInflater.from(PostDetailActivity.this);

        // ?????? ???????????? ??? ArrayList??? Adapter ??????
        comments = new ArrayList<ListComments>();
        adapter_comments = new ListCommentsAdapter(comments);

        // recyclerView ??? ????????? ?????? ????????? ??????
        adapter_comments.setOnItemClickListener(new ListCommentsAdapter.OnItemClickListener() {

            @Override
            public void onCommentDeleteClick(View v, int position) {
                // ????????? index ??? ????????? ??? ?????? ????????? ?????? ?????? ??????
                if (position == comments.size() - 1) {
                    comments.remove(position);
                    Toast.makeText(getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                    adapter_comments.notifyDataSetChanged();
                    return;
                }
                // ????????? ?????? ???????????? ???????????? ????????? ??????
                if (comments.get(position + 1).getViewType() == ListCommentsViewType.REPLY) {
                    comments.remove(position);
                    comments.add(position, new ListComments("????????? ???????????????.", "", "",
                            ListCommentsViewType.COMMENT));
                    adapter_comments.notifyItemChanged(position); // ?????? ????????? ?????? ??????????????? ??????
                } else {
                    comments.remove(position);
                }
                Toast.makeText(getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                adapter_comments.notifyDataSetChanged();
            }

            @Override
            public void onCommentReplyClick(View v, int position) {
                String text = ((TextView) v).getText().toString();
                Intent intent = new Intent(getApplicationContext(),
                        CommentReplyActivity.class);
                intent.putExtra("userid", comments.get(position).getUserId());
                intent.putExtra("content", comments.get(position).getContent());
                intent.putExtra("time", comments.get(position).getTime());
                intent.putExtra("index", position);
                resultLauncher.launch(intent);
            }

            @Override
            public void onCommentReplyDeleteClick(View v, int position) {

                // ??? ????????? index??? ??? ???????????? ????????? ?????? ????????? ?????? ?????? ??????
                if (position == comments.size() - 1) {
                    comments.remove(position);
                    comments.remove(position - 1);
                    Toast.makeText(getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                    adapter_comments.notifyDataSetChanged();
                    return;
                }

                // ????????? ????????? ??? ????????? ??????????????? ???
                if (comments.get(position - 1).getContent().equals("????????? ???????????????.") &&
                        (comments.get(position + 1).getViewType() == ListCommentsViewType.COMMENT)) {
                    comments.remove(position);
                    comments.remove(position - 1);
                } else
                    comments.remove(position);
                Toast.makeText(getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                adapter_comments.notifyDataSetChanged();
            }
        });

        // recyclerView??? Adapter ??????
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activity_postdetail_rv_comments.setLayoutManager(manager); // connect LayoutManager
        activity_postdetail_rv_comments.setAdapter(adapter_comments); // connect adapter

        getPostDetailInformation();

        // ?????? ???????????? ????????? ????????? ??? ?????????
        activity_postdetail_btn_commentok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = activity_postdetail_et_comment.getText().toString();


                // array??? ???????????? ????????? ????????????
                comments.add(new ListComments(text, "test_nickname", "2021.11.23 23:43",
                        ListCommentsViewType.COMMENT));
                adapter_comments.notifyDataSetChanged();


                // ????????? ?????????, getText ????????????
                activity_postdetail_et_comment.getText().clear();
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        // ?????????????????? ???????????? ??? ???????????? resultLauncher ??????
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    String userid = intent.getStringExtra("userid");
                    String time = intent.getStringExtra("time");
                    String content = intent.getStringExtra("content");
                    int index = intent.getIntExtra("index", 0);

                    // ????????? index ????????? ???????????? ?????? ???????????? ????????? ??????
                    if (index == comments.size()) {
                        comments.add(index, new ListComments(content, userid, time,
                                ListCommentsViewType.REPLY));
                        adapter_comments.notifyDataSetChanged();
                        return;
                    }

                    // ????????? ???????????? ?????? ?????? ??????????????? ????????? ????????? ??? index ??????
                    while (true) {
                        if (index == comments.size())
                            break;
                        if (comments.get(index).getViewType() == ListCommentsViewType.COMMENT)
                            break;
                        index++;
                    }

                    // array ??? ?????? ???????????? ????????????
                    comments.add(index, new ListComments(content, userid, time,
                            ListCommentsViewType.REPLY));
                    adapter_comments.notifyDataSetChanged();
                }
            } // end on ActivityResult
        }); // end ResultLauncher

//        // TODO: ???????????? ????????? ??? ?????? ??? ???????????? "??????" TEXTVIEW??? ????????? ??? ?????? ???????????? ?????? ?????????????

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
