package com.with.us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
    ArrayList<com.with.us.ListItem> items= new ArrayList<>();
    Context context;
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context=parent.getContext();
        com.with.us.ListItem listItem=items.get(position);
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.activity_list_item,parent,false);
        }
        TextView nameText=convertView.findViewById(R.id.text1);
        TextView descriptionText=convertView.findViewById(R.id.text2);
        TextView scoreText=convertView.findViewById(R.id.textView4);
        TextView numberText=convertView.findViewById(R.id.textView3);

        nameText.setText(listItem.getName());
        descriptionText.setText(listItem.getDescription());
        scoreText.setText(listItem.getScore());
        numberText.setText(listItem.getNumber());

        return convertView;
    }
    public void addItem(com.with.us.ListItem item){
        items.add(item);
    }
}