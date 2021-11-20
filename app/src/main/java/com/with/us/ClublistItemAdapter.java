package com.with.us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ClublistItemAdapter extends BaseAdapter {
    ArrayList<ClublistItem> items = new ArrayList<>();
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
        context = parent.getContext();
        ClublistItem listItem = items.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_clublist_item, parent, false);
        }

        TextView nameText = convertView.findViewById(R.id.list_title);
        TextView descriptionText = convertView.findViewById(R.id.list_description);
        TextView scoreText = convertView.findViewById(R.id.list_score);
        TextView numberText = convertView.findViewById(R.id.list_number);

        nameText.setText(listItem.getName());
        descriptionText.setText(listItem.getDescription());
        scoreText.setText(listItem.getScore());
        numberText.setText(listItem.getNumber());

        return convertView;
    }

    public void addItem(ClublistItem item) {
        items.add(item);
    }
}
