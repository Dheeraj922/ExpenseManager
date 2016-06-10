package com.dheeraj.actitproject.DatabaseClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dheeraj.actitproject.interfaces.Constants;

/**
 * Created by DHEERAJ on 2/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements Constants {
    String query;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        query="CREATE TABLE "+TABLE_NAME+"("+NAME+" TEXT NOT NULL,"+COST+" NUMBER NOT NULL,"+DATE+" TEXT NOT NULL);";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
