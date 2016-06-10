package com.dheeraj.actitproject.userinterface;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.dheeraj.actitproject.Adapters.ExpandableListAdapter;
import com.dheeraj.actitproject.R;
import com.dheeraj.actitproject.interfaces.PrevExpDialogListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PrevExpDialogListener {
    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    List<String> dataHeader;
    HashMap<String,List<String>> dataChild;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                Intent intent=new Intent(this,AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (ExpandableListView) findViewById(R.id.mainList);
        prepareListData();

        listAdapter=new ExpandableListAdapter(this,dataHeader,dataChild);

        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {

                    switch (childPosition) {
                        case 0:
                            Intent intent = new Intent(MainActivity.this, AddItemsUI.class);
                            intent.putExtra("selectedDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                            startActivity(intent);
                            break;
                        case 1:
                            DialogFragment fragment = new ShowPrevExpDialog();
                            fragment.show(getFragmentManager(), "DatePickerDialogFragment");
                            break;
                    }
                } else if (groupPosition == 1) {

                    switch (childPosition) {
                        case 0:
                            Intent intent = new Intent(MainActivity.this, RemoveItemsActivity.class);
                            intent.putExtra("CurrentDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                            startActivity(intent);
                            break;
                        case 1:
                            DialogFragment fragment = new ShowPrevExpDialog();
                            fragment.show(getFragmentManager(), "PreviousDatePickerDialog");
                            break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onPrevDateSelected(DialogFragment fragment,String selectedDate) {

        if ((fragment.getTag()).equalsIgnoreCase("DatePickerDialogFragment")) {
            Intent intent = new Intent(MainActivity.this, ViewPreviousExpActivity.class);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        }
        else if ((fragment.getTag()).equalsIgnoreCase("PreviousDatePickerDialog")){
            Intent i=new Intent(MainActivity.this,ViewPreviousExpActivity.class);
            i.putExtra("previousDate",selectedDate);
            startActivity(i);

        }

    }

    private void prepareListData() {
        dataHeader=new ArrayList<String>();
        dataChild=new HashMap<String,List<String>>();

        //Adding the headers of each list Item
        dataHeader.add("Add");
        dataHeader.add("Edit");

        //Adding the child data into headers
        List<String> addItems=new ArrayList<>();
        addItems.add("Add In Today");
        addItems.add("Add In Previous");

        List<String> editItems=new ArrayList<>();
        editItems.add("Edit Today's expenditure");
        editItems.add("Edit Previous expenditure");

        dataChild.put(dataHeader.get(0), addItems);
        dataChild.put(dataHeader.get(1),editItems);


    }


}
