package com.with.us;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListCategoryAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private int groupLayout = 0;
    private int childLayout = 0;
    private ArrayList<ListCategory> DataList;
    private LayoutInflater mInf = null;

    public ListCategoryAdapter(Context context, int groupLayout, int childLayout, ArrayList<ListCategory> DataList) {
        this.DataList = DataList;
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;
        this.mContext = context;
        this.mInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // test
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = mInf.inflate(this.groupLayout, parent, false);
        }
        TextView groupName = (TextView) convertView.findViewById(R.id.list_group);
        groupName.setText(DataList.get(groupPosition).getGroupName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = mInf.inflate(this.childLayout, parent, false);
        }
        TextView childName = (TextView) convertView.findViewById(R.id.list_child);
        childName.setText(DataList.get(groupPosition).getChild().get(childPosition));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).getChild().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).getChild().size();
    }

    @Override
    public ListCategory getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

}
