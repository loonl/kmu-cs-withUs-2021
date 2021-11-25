package com.with.us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ListComments> mDataList = null;

    ListCommentsAdapter(ArrayList<ListComments> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == ListCommentsViewType.COMMENT) // = 0
        {
            view = inflater.inflate(R.layout.item_postdetail_comments, parent, false);
            return new CommentViewHolder(view);
        } else { // viewType == ListCommentsViewType.REPLY ( = 1)
            view = inflater.inflate(R.layout.item_postdetail_replies, parent, false);
            return new ReplyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CommentViewHolder) {
            ((CommentViewHolder) viewHolder).content.setText(mDataList.get(position).getContent());
            ((CommentViewHolder) viewHolder).time.setText(mDataList.get(position).getTime());
            ((CommentViewHolder) viewHolder).userid.setText(mDataList.get(position).getUserId());
        } else { // viewHolder instanceof ReplyViewHolder
            ((ReplyViewHolder) viewHolder).content.setText(mDataList.get(position).getContent());
            ((ReplyViewHolder) viewHolder).time.setText(mDataList.get(position).getTime());
            ((ReplyViewHolder) viewHolder).userid.setText(mDataList.get(position).getUserId());
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getViewType();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView content, userid, time;

        CommentViewHolder(View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.text_comment_content);
            userid = itemView.findViewById(R.id.text_comment_userid);
            time = itemView.findViewById(R.id.text_comment_time);
        }
    }

    public class ReplyViewHolder extends RecyclerView.ViewHolder {
        TextView content, userid, time;

        ReplyViewHolder(View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.text_commentreply_content);
            userid = itemView.findViewById(R.id.text_commentreply_userid);
            time = itemView.findViewById(R.id.text_commentreply_time);
        }
    }
}
