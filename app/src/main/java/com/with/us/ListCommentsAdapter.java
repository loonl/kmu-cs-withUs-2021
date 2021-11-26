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
    private OnItemClickListener mListener = null; // 리스너 객체 참조 변수

    // 리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // 댓글 UI 뷰홀더
    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView content, userid, time, tv_delete, tv_reply;

        CommentViewHolder(View itemView) {
            super(itemView);

            // click 이벤트 만들어줄 textView 연결
            tv_delete = (TextView) itemView.findViewById(R.id.text_comment_delete);
            tv_reply = (TextView) itemView.findViewById(R.id.text_comment_reply);

            content = itemView.findViewById(R.id.text_comment_content);
            userid = itemView.findViewById(R.id.text_comment_userid);
            time = itemView.findViewById(R.id.text_comment_time);

            // "삭제" 텍스트 클릭 이벤트
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null)
                            mListener.onCommentDeleteClick(view, position);
                    }
                }
            }); // end tv_delete setOnClickListener

            // "답글" 텍스트 클릭 이벤트
            tv_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onCommentReplyClick(view, position);
                        }
                    } // end if
                }
            }); // end tv_reply setOnClickListener
        } // end commentViewHolder
    }

    // 답글 UI 뷰홀더
    public class ReplyViewHolder extends RecyclerView.ViewHolder {
        TextView content, userid, time, tv_replydelete;

        ReplyViewHolder(View itemView) {
            super(itemView);

            // click 이벤트 만들어줄 textView 연결
            tv_replydelete = (TextView) itemView.findViewById(R.id.text_commentreply_delete);

            content = itemView.findViewById(R.id.text_commentreply_content);
            userid = itemView.findViewById(R.id.text_commentreply_userid);
            time = itemView.findViewById(R.id.text_commentreply_time);

            // "삭제" 텍스트 클릭 이벤트
            tv_replydelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onCommentReplyDeleteClick(view, position);
                        }
                    } // end if
                }
            }); // end tv_replydelete setOnClickListener
        }
    } // end ReplyViewHolder

    // 생성자
    ListCommentsAdapter(ArrayList<ListComments> dataList) {
        mDataList = dataList;
    }

    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListener {
        void onCommentDeleteClick(View v, int position);

        void onCommentReplyClick(View v, int position);

        void onCommentReplyDeleteClick(View v, int position);
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
            CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
            commentViewHolder.content.setText(mDataList.get(position).getContent());
            commentViewHolder.time.setText(mDataList.get(position).getTime());
            commentViewHolder.userid.setText(mDataList.get(position).getUserId());
        } else { // viewHolder instanceof ReplyViewHolder
            ReplyViewHolder replyViewHolder = (ReplyViewHolder) viewHolder;
            replyViewHolder.content.setText(mDataList.get(position).getContent());
            replyViewHolder.time.setText(mDataList.get(position).getTime());
            replyViewHolder.userid.setText(mDataList.get(position).getUserId());
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
}
