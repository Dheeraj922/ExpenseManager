package com.dheeraj.actitproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dheeraj.actitproject.R;
import com.dheeraj.actitproject.interfaces.CheckedItemListener;
import com.dheeraj.actitproject.models.RemoveItemsModel;

import java.util.List;

/**
 * Created by DHEERAJ on 2/21/2016.
 */
public class RemoveItemsAdapter extends ArrayAdapter<RemoveItemsModel> {
    private List<RemoveItemsModel> list;
    private Context context;
    boolean checkAll_flag=false;
    boolean checkItem_flag=false;
    CheckedItemListener listener;
    long count=0;

    public RemoveItemsAdapter(Context context,  List<RemoveItemsModel> list) {
        super(context, R.layout.activity_remove_items, list);
        this.context=context;
        this.list=list;
        listener= (CheckedItemListener) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.remove_list_item,parent,false);
            holder.nameText= (TextView) convertView.findViewById(R.id.itemNameText);
            holder.costText= (TextView) convertView.findViewById(R.id.costText);
            holder.deleteBox= (CheckBox) convertView.findViewById(R.id.deleteBox);
            holder.deleteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        count++;
                    }
                    else if (!(isChecked)){
                        count--;
                    }
                    int position= (int) buttonView.getTag();//getting the position that we have set for checkbox using setTag()
                    list.get(position).setSelected(buttonView.isChecked());//set the value to maintain its state
                    listener.onCheckedItemListener(list.get(position).getName(),isChecked,count);
                }
            });
            convertView.setTag(holder);
            convertView.setTag(R.id.itemNameText, holder.nameText);
            convertView.setTag(R.id.costText, holder.costText);
            convertView.setTag(R.id.deleteBox,holder.deleteBox);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.deleteBox.setTag(position);//important line
        holder.nameText.setText("Item name: "+list.get(position).getName());
        holder.costText.setText("Item cost: "+list.get(position).getCost());
        holder.deleteBox.setChecked(list.get(position).isSelected());

        return convertView;
    }

    class ViewHolder{
        private TextView nameText;
        private TextView costText;
        private CheckBox deleteBox;
    }
}
