package com.dheeraj.actitproject.userinterface;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dheeraj.actitproject.Adapters.ListDataAdapter;
import com.dheeraj.actitproject.DatabaseClasses.DatabaseHelper;
import com.dheeraj.actitproject.R;
import com.dheeraj.actitproject.interfaces.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddItemsUI extends AppCompatActivity implements Constants {
    private EditText name;
    private EditText cost;
    private ArrayList<String> nameList;
    private ArrayList<String> costList;
    private Button addItemsButton;
    private Button cancelButton;
    private TextView notifyText;
    ListDataAdapter adapter;
    ListView displayList;
    String date;
    String prevDate;
    String dateData;
    TextView showTotal;
    int count=0;
    private Bundle bundle;
    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items_ui);

        bundle=getIntent().getExtras();
        dateData=bundle.getString("selectedDate");
        Button addButton= (Button) findViewById(R.id.addButton);
        Button editButton= (Button) findViewById(R.id.editButton);
        dateView= (TextView) findViewById(R.id.showDate);
        notifyText= (TextView) findViewById(R.id.notifyText);
        displayList= (ListView) findViewById(R.id.displayList);
        showTotal= (TextView) findViewById(R.id.showTotal);

        if (dateData.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))){
            date=dateData;
            dateView.setText("Date: "+date);
            nameList=getNameData(date);
            costList=getCostData(date);
            if (nameList.isEmpty()) {
                notifyText.setVisibility(View.VISIBLE);
            }
            else {
                adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                adapter.notifyDataSetChanged();

                displayList.setAdapter(adapter);
                showTotal.setVisibility(View.VISIBLE);
                long result = getTotalSum(date);
                showTotal.setText("Total:  " + result);
            }

        }

        else {
            prevDate=dateData;
            dateView.setText("Date: " + prevDate);
            nameList=getNameData(prevDate);
            costList=getCostData(prevDate);
            if (nameList.isEmpty()) {
                notifyText.setVisibility(View.VISIBLE);
            }
            else {
                adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                adapter.notifyDataSetChanged();

                displayList.setAdapter(adapter);
                showTotal.setVisibility(View.VISIBLE);
                long result = getTotalSum(prevDate);
                showTotal.setText("Total:  " + result);
            }
        }



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemsDialog();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (dateData.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(new Date())))  {
                 Intent intent=new Intent(AddItemsUI.this,RemoveItemsActivity.class);
                 intent.putExtra("CurrentDate",dateData);
                 startActivity(intent);
             }
                else{
                 Intent intent=new Intent(AddItemsUI.this,RemoveItemsActivity.class);
                 intent.putExtra("removeDate",dateData);
                 startActivity(intent);
             }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dateData.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))){
            date=dateData;
            dateView.setText("Date: "+date);
            nameList=getNameData(date);
            costList=getCostData(date);
            if (nameList.isEmpty()) {
                notifyText.setVisibility(View.VISIBLE);
                showTotal.setVisibility(View.GONE);
                //adapter.notifyDataSetChanged();
                adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                displayList.setAdapter(adapter);
            }
            else {
                adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                adapter.notifyDataSetChanged();

                displayList.setAdapter(adapter);
                showTotal.setVisibility(View.VISIBLE);
                long result = getTotalSum(date);
                showTotal.setText("Total:  " + result);
            }

        }

        else {
            prevDate=dateData;
            dateView.setText("Date: " + prevDate);
            nameList=getNameData(prevDate);
            costList=getCostData(prevDate);
            if (nameList.isEmpty()) {
                notifyText.setVisibility(View.VISIBLE);
                showTotal.setVisibility(View.GONE);
                //adapter.notifyDataSetChanged();
                adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                displayList.setAdapter(adapter);
            }
            else {
                adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                adapter.notifyDataSetChanged();

                displayList.setAdapter(adapter);
                showTotal.setVisibility(View.VISIBLE);
                long result = getTotalSum(prevDate);
                showTotal.setText("Total:  " + result);
            }
        }

    }

    //Method for custom Dialog

    private void showItemsDialog() {

        LayoutInflater inflater=this.getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.custom_items_dialog, null);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        name= (EditText) dialogView.findViewById(R.id.itemName);
        cost= (EditText) dialogView.findViewById(R.id.itemCost);
        addItemsButton= (Button) dialogView.findViewById(R.id.addItemsButton);
        cancelButton= (Button) dialogView.findViewById(R.id.cancelButton);
        builder.setView(dialogView);
        final AlertDialog dialog=builder.create();
        dialog.show();
        addItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()))
                    name.setError("Please enter name");
                else if (TextUtils.isEmpty(cost.getText()))
                    cost.setError("Please enter cost");
                else {
                    insertIntoDatabase();
                    name.setText("");
                    cost.setText("");
                    cost.clearFocus();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!(dateData.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy").format(new Date())))) {
                    prevDate=dateData;
                    nameList = getNameData(prevDate);
                    costList = getCostData(prevDate);
                    if (nameList.isEmpty()) {
                        notifyText.setVisibility(View.VISIBLE);
                        showTotal.setVisibility(View.GONE);
                    } else {
                        notifyText.setVisibility(View.GONE);
                        adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                        adapter.notifyDataSetChanged();

                        displayList.setAdapter(adapter);
                        showTotal.setVisibility(View.VISIBLE);
                        long result = getTotalSum(prevDate);
                        showTotal.setText("Total:  " + result);
                    }
                } else {
                    date=dateData;
                    nameList = getNameData(date);
                    costList = getCostData(date);
                    if (nameList.isEmpty()) {
                        notifyText.setVisibility(View.VISIBLE);
                        showTotal.setVisibility(View.GONE);
                    } else {
                        notifyText.setVisibility(View.GONE);
                        adapter = new ListDataAdapter(AddItemsUI.this, nameList, costList);
                        adapter.notifyDataSetChanged();

                        displayList.setAdapter(adapter);
                        showTotal.setVisibility(View.VISIBLE);
                        long result = getTotalSum(date);
                        showTotal.setText("Total:  " + result);
                    }
                }

            }
        });
        builder.setCancelable(false);
        dialog.setCancelable(false);

    }



    private long getTotalSum(String date) {
        long result=0;
        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+DATE+"='"+date+"'",null);
        while(cursor.moveToNext()){
            result=result+cursor.getLong(1);
        }
        db.close();
        return result;
    }


    private void insertIntoDatabase(){
        DatabaseHelper helper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(NAME, name.getText().toString());
        values.put(COST, cost.getText().toString());
        values.put(DATE, dateData);

        db.insertOrThrow(TABLE_NAME, null, values);
        db.close();
    }


    public ArrayList<String> getNameData(String date){

        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DATE + "='" +date + "'", null);
        ArrayList<String> nameList=new ArrayList<>();
        while(cursor.moveToNext()){
            //count++;
            //if (count!=0)
                nameList.add(cursor.getString(0));
        }
        db.close();
        return nameList;
    }



    public ArrayList<String> getCostData(String date){
        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+DATE+"='"+date+"'", null);
        ArrayList<String> costList=new ArrayList<>();
        while(cursor.moveToNext()){
           // if ((cursor.getString(2)).equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
                costList.add(cursor.getString(1));

        }
        db.close();
        return costList;
    }


}
