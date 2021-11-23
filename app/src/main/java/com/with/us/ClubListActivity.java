package com.with.us;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
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
    ClubListItemAdapter adapter;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clublist);
        category = getIntent().getStringExtra("category");


        ArrayList<String> list = new ArrayList<>();
        list.add("태그1");
        list.add("태그2");
        list.add("태그3");
        list.add("태그4");
        list.add("태그5");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ClubListTagAdapter tagAdapter = new ClubListTagAdapter(list);
        recyclerView.setAdapter(tagAdapter);

        listView = findViewById(R.id.listview);
        adapter = new ClubListItemAdapter();
        getPost(listView, adapter);

    }

    private void getPost(ListView listView, ClubListItemAdapter adapter) {
        RequestHelper.getPostAPI().getPostDetail("Bearer " + FirebaseHelper.getAccessToken(this), category)
                .enqueue(new Callback<List<PostDetail>>() {
                    @Override
                    public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                        if (response.isSuccessful()) {
                            List<PostDetail> posts = response.body();
                            for (PostDetail post : posts) {
                                adapter.addItem(new ClubListItem(post.title, post.content, post.likes, post.comments, post.postImage));
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
}