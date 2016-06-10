package com.dheeraj.actitproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dheeraj.actitproject.R;

import java.util.ArrayList;

/**
 * Created by DHEERAJ on 2/14/2016.
 */
public class ListDataAdapter extends ArrayAdapter<String> {
    ArrayList<String> nameList;
    ArrayList<String> costList;
    Context context;

    public ListDataAdapter(Context context, ArrayList<String> nameList, ArrayList<String> costList) {
        super(context, R.layout.activity_display_iist, nameList);
        this.context = context;
        this.nameList = nameList;
        this.costList = costList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.display_list_item, parent, false);
            holder.nameInfo = (TextView) convertView.findViewById(R.id.item);
            holder.costInfo = (TextView) convertView.findViewById(R.id.cost);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameInfo.setText("Item name: "+nameList.get(position));
        holder.costInfo.setText("Item cost: "+costList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView nameInfo;
        TextView costInfo;
    }

    public void refresh(ArrayList<String> nameList, ArrayList<String> costList) {
        this.nameList.clear();
        this.costList.clear();
        this.nameList.addAll(nameList);
        this.costList.addAll(costList);
        notifyDataSetChanged();
    }
}

