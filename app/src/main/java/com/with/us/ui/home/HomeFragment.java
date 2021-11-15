package com.with.us.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.with.us.R;
import com.with.us.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    SearchView sv_main;
    ListView lv_hot;
    ListView lv_new;
    ArrayList<String> hotList = new ArrayList<>(Arrays.asList("Hot1", "Hot2", "Hot3"));
    ArrayList<String> newList = new ArrayList<>(Arrays.asList("New1", "New2", "New3"));

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sv_main = view.findViewById(R.id.sv_main);
        lv_hot = view.findViewById(R.id.lv_hot);
        lv_new = view.findViewById(R.id.lv_new);

        makeList(lv_hot, hotList);
        makeList(lv_new, newList);

        return view;
    }

    private void makeList(ListView listView, ArrayList<String> arrayList) {
        List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        list.addAll(arrayList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}