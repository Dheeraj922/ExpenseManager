package com.dheeraj.actitproject.userinterface;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dheeraj.actitproject.Adapters.RemoveItemsAdapter;
import com.dheeraj.actitproject.DatabaseClasses.DatabaseHelper;
import com.dheeraj.actitproject.R;
import com.dheeraj.actitproject.interfaces.CheckedItemListener;
import com.dheeraj.actitproject.interfaces.Constants;
import com.dheeraj.actitproject.models.RemoveItemsModel;

import java.util.ArrayList;
import java.util.List;

public class RemoveItemsActivity extends AppCompatActivity implements Constants,CheckedItemListener {
    private ListView removeList;
    private TextView removeDate;
    private TextView removeItemsNotifyText;
    private Button deleteButton;
    String date;
    Bundle bundle;
    private ArrayList<String> names;
    private List<RemoveItemsModel> list;
    ArrayAdapter<RemoveItemsModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_items);
        removeList = (ListView) findViewById(R.id.removeItemsList);
        removeDate = (TextView) findViewById(R.id.removeItemsDate);
        deleteButton= (Button) findViewById(R.id.deleteButton);
        removeItemsNotifyText= (TextView) findViewById(R.id.removeItemsNotifyText);
        names=new ArrayList<>();
        bundle=getIntent().getExtras();
        if (bundle.containsKey("CurrentDate")) {
            date = bundle.getString("CurrentDate");
            removeDate.setText("Date: " + date);
            list=getModel(date);
            if (list.isEmpty()) {
                removeItemsNotifyText.setVisibility(View.VISIBLE);
                removeItemsNotifyText.setText("No items for the day.Please go back to ADD section to add items");

            }
            else{
                adapter = new RemoveItemsAdapter(this, list);
                removeList.setAdapter(adapter);
            }

        }
        else if (bundle.containsKey("removeDate")){
            date=bundle.getString("removeDate");
            removeDate.setText("Date: " + date);
            list=getModel(date);
            if (list.isEmpty()) {
                removeItemsNotifyText.setVisibility(View.VISIBLE);
                removeItemsNotifyText.setText("No items for the day.Please go back to ADD section to add items");

            }
            else{
                adapter = new RemoveItemsAdapter(this, list);
                removeList.setAdapter(adapter);
            }
        }
    }


    private List<RemoveItemsModel> getModel(String date) {
        DatabaseHelper helper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=helper.getReadableDatabase();
        List<RemoveItemsModel> list=new ArrayList<RemoveItemsModel>();
        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DATE + "='" + date + "'", null);
        while(cursor.moveToNext()){
            list.add(new RemoveItemsModel(cursor.getString(0),cursor.getString(1)));
        }
        db.close();
        return list;

    }

    @Override
    public void onCheckedItemListener(String itemName,boolean isChecked,long count) {
        if (isChecked || count>0){
            deleteButton.setVisibility(View.VISIBLE);
        }
        else if (count==0) {
            deleteButton.setVisibility(View.GONE);
        }
        if (isChecked){
            names.add(itemName);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DialogFragment fragment = new ConfirmDialogFragment();
                //fragment.show(getFragmentManager(), "deleteItems");
                deleteItemsFromDatabase(names);
                //showDialog(names);
                names.clear();
            }
        });

    }
    private void showDialog(final ArrayList<String> names){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog=builder.create();
        builder.setMessage("Are you sure?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }

                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();

                    }
                }).show();
    }


    private void deleteItemsFromDatabase(ArrayList<String> names) {
        int i;
        DatabaseHelper helper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=helper.getReadableDatabase();
        for (i=0;i<names.size();i++){
            //db.delete(TABLE_NAME, NAME + "=" + names.get(i), null);
            db.execSQL("DELETE FROM "+ TABLE_NAME+ " WHERE "+ NAME+ "='"+names.get(i)+"'");
        }
        db.close();
        if (i==1)
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Items removed",Toast.LENGTH_SHORT).show();

        deleteButton.setVisibility(View.GONE);
        if (bundle.containsKey("CurrentDate")) {
            date = bundle.getString("CurrentDate");
            removeDate.setText("Date: " + date);
            list=getModel(date);
            if (list.isEmpty()) {
                removeItemsNotifyText.setVisibility(View.VISIBLE);
                removeItemsNotifyText.setText("No Items left");
                adapter = new RemoveItemsAdapter(this, list);
                removeList.setAdapter(adapter);

            }

            else{
                adapter = new RemoveItemsAdapter(this, list);
                adapter.notifyDataSetChanged();
                removeList.setAdapter(adapter);
            }

        }
        else if (bundle.containsKey("removeDate")){
            date=bundle.getString("removeDate");
            removeDate.setText("Date: " + date);
            list=getModel(date);
            if (list.isEmpty()) {
                removeItemsNotifyText.setVisibility(View.VISIBLE);
                removeItemsNotifyText.setText("No Items left");
                adapter = new RemoveItemsAdapter(this, list);
                removeList.setAdapter(adapter);

            }
            else{
                adapter = new RemoveItemsAdapter(this, list);
                adapter.notifyDataSetChanged();
                removeList.setAdapter(adapter);
            }
        }

    }


}


