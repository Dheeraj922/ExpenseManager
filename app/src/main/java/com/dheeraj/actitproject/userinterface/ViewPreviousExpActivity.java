package com.dheeraj.actitproject.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dheeraj.actitproject.R;
import com.dheeraj.actitproject.interfaces.Constants;

public class ViewPreviousExpActivity extends AppCompatActivity implements Constants{
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_iist);
        TextView displayDate= (TextView) findViewById(R.id.showPreviousDateText);
        Button displayItems= (Button) findViewById(R.id.showPreviousDateButton);

        Bundle bundle=getIntent().getExtras();
        if (bundle.containsKey("date")) {
            date = bundle.getString("date");
            displayItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ViewPreviousExpActivity.this,AddItemsUI.class);
                    intent.putExtra("selectedDate",date);
                    startActivity(intent);
                }
            });
        }
        else if (bundle.containsKey("previousDate")){
            date=bundle.getString("previousDate");
            displayItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ViewPreviousExpActivity.this,RemoveItemsActivity.class);
                    intent.putExtra("removeDate",date);
                    startActivity(intent);
                }
            });
        }
        displayDate.setText("Date: " + date);
    }


}
