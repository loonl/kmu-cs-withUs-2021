package com.with.us;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.with.us.models.PostDetail;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubListActivity extends AppCompatActivity {

    private static final String TAG = "ClubListActivity";

    ListView listView;
    FloatingActionButton fab;
    ClubListItemAdapter adapter;
    String CATEGORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clublist);
        CATEGORY = getIntent().getStringExtra("category");
        fab = findViewById(R.id.fab_write_post);

        ArrayList<String> list = new ArrayList<>();
        list.add("태그1");
        // list.add("태그2");
        // list.add("태그3");
        // list.add("태그4");
        // list.add("태그5");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ClubListTagAdapter tagAdapter = new ClubListTagAdapter(list);
        recyclerView.setAdapter(tagAdapter);

        listView = findViewById(R.id.listview);
        adapter = new ClubListItemAdapter();
        getPost(listView, adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClubListActivity.this, PostDetailActivity.class);
                intent.putExtra("uid", adapter.getUid(position));
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseHelper.getUserEmail().equals("Anonymous")) {
                    new AlertDialog.Builder(ClubListActivity.this).setMessage("로그인이 필요합니다.")
                            .setPositiveButton("확인", null)
                            .setNegativeButton("로그인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ClubListActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                    return;
                }
                Intent intent = new Intent(ClubListActivity.this, PostFormActivity.class);
                intent.putExtra("category", CATEGORY);
                startActivity(intent);
            }
        });

    }

    private void getPost(ListView listView, ClubListItemAdapter adapter) {
        RequestHelper.getPostAPI()
                .getPost("Bearer " + FirebaseHelper.getAccessToken(ClubListActivity.this), CATEGORY)
                .enqueue(new Callback<List<PostDetail>>() {
                    @Override
                    public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                        if (response.isSuccessful()) {
                            List<PostDetail> posts = response.body();
                            for (PostDetail post : posts) {
                                adapter.addItem(new ClubListItem(post.uid, post.title, post.content, post.likes, post.comments,
                                        post.postImage));
                            }
                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PostDetail>> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                        // TODO: "게시글이 존재하지 않습니다"로 텍스트 보여주기
                    }
                });
    }

    // @Override
    // protected void onResume() {
    // super.onResume();
    // }
}