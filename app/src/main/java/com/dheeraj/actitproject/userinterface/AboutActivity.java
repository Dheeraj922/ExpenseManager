package com.dheeraj.actitproject.userinterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dheeraj.actitproject.R;

public class AboutActivity extends AppCompatActivity {

    String text="Handy app for managing your daily expenses"+"\n\n" +"Developed By:Dheeraj Choudhary" + "\n\n" +"Version:1.0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView textView= (TextView) findViewById(R.id.aboutText);
        textView.setText(text);
        textView.setTextSize(25);
    }
}
