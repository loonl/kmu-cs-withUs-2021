package com.with.us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListTagAdapter extends RecyclerView.Adapter<ListTagAdapter.ViewHolder> {
    private ArrayList<String> mData;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        ViewHolder(View itemview){
            super(itemview);

            textView1=itemview.findViewById(R.id.text);
        }
    }
    ListTagAdapter(ArrayList<String> list){
        mData=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.activity_list_tag,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String text=mData.get(position);
        holder.textView1.setText(text);
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }
}
