package com.dheeraj.actitproject.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dheeraj.actitproject.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by DHEERAJ on 2/14/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> dataHeader;   //header titles
    private HashMap<String,List<String>> dataChild;    //child items in format of header title and child title

    public ExpandableListAdapter(Context context, List<String> dataHeader, HashMap<String, List<String>> dataChild) {
        this.context = context;
        this.dataHeader = dataHeader;
        this.dataChild = dataChild;
    }

    @Override
    public int getGroupCount() {
        return this.dataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.dataChild.get(this.dataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.dataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.dataChild.get(this.dataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle= (String) getGroup(groupPosition);
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_group_header,null);
        }
        TextView listHeader= (TextView) convertView.findViewById(R.id.listHeader);
        listHeader.setTypeface(null, Typeface.BOLD_ITALIC);
        listHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText= (String) getChild(groupPosition,childPosition);
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_item_child,null);
        }
        TextView listChild= (TextView) convertView.findViewById(R.id.listChildItem);
       // listHeader.setTypeface(null, Typeface.BOLD_ITALIC);
        listChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
