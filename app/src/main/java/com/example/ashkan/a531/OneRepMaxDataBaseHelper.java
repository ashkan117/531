package com.example.ashkan.a531;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ashkan.a531.ContractClass.OneRepMaxEntry;

import static android.R.attr.data;

/**
 * Created by Ashkan on 12/20/2017.
 */

public class OneRepMaxDataBaseHelper extends SQLiteOpenHelper {

    //simplifies constructor and might change so we use fields
    public static final String TABLE_NAME="oneRepMax.db";
    public static final int DATABASE_VERSION=1;

    public OneRepMaxDataBaseHelper(Context context,  int version) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
