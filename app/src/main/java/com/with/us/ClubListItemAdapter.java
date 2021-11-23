package com.with.us;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClubListItemAdapter extends BaseAdapter {
    ArrayList<ClubListItem> items = new ArrayList<>();
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
        ClubListItem listItem = items.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_clublist_item, parent, false);
        }

        TextView title = convertView.findViewById(R.id.activity_club_list_title);
        TextView content = convertView.findViewById(R.id.activity_club_list_content);
        TextView likes = convertView.findViewById(R.id.activity_club_list_likes);
        TextView comments = convertView.findViewById(R.id.activity_club_list_comments);
        ImageView imageView = convertView.findViewById(R.id.list_title_image);

        title.setText(listItem.getTitle());
        content.setText(listItem.getContent());
        likes.setText(String.valueOf(listItem.getLikes()));
        comments.setText(String.valueOf(listItem.getComments()));
        Picasso.get().load(Uri.parse(listItem.getPostImage())).fit().into(imageView);

        return convertView;
    }

    public void addItem(ClubListItem item) {
        items.add(item);
    }
}