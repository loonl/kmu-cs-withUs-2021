package com.with.us;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.with.us.R;
import com.with.us.databinding.FragmentHomeBinding;
import com.with.us.models.PostDetail;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    SearchView sv_main;
    ListView lv_hot;
    ListView lv_new;
    ArrayList<PostDetail> hotList = new ArrayList<>();
    ArrayList<PostDetail> newList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sv_main = view.findViewById(R.id.sv_main);
        lv_hot = view.findViewById(R.id.lv_hot);
        lv_new = view.findViewById(R.id.lv_new);

////        makeList(lv_hot, hotList);
//        makeList(lv_new, newList);

        return view;
    }

//    private void makeList(ListView listView, ArrayList<PostDetail> arrayList, PostDetail postDetail) {
//        List<PostDetail> list = new ArrayList<>();
//        ArrayAdapter<PostDetail> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(adapter);
//        list.addAll(arrayList);
//    }
//
//    private void getLatestPost() {
//        RequestHelper.getPostAPI().getLatestPost("Bearer " + FirebaseHelper.getAccessToken(getContext()))
//                .enqueue(new Callback<List<PostDetail>>() {
//                    @Override
//                    public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
//                        if (response.isSuccessful()) {
//                            List<PostDetail> posts = response.body();
//                            for (PostDetail post : posts) {
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<PostDetail>> call, Throwable t) {
//                        Log.e(TAG, t.getMessage());
//                        // TODO: "게시글이 존재하지 않습니다"로 텍스트 보여주기
//                    }
//                });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}