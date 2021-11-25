package com.with.us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClubListTagAdapter extends RecyclerView.Adapter<ClubListTagAdapter.ViewHolder> {
    private ArrayList<String> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemview) {
            super(itemview);
            textView = itemview.findViewById(R.id.tag_name);
        }
    }

    ClubListTagAdapter(ArrayList<String> list) {
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_clublist_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
