package com.example.ashkan.a531.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ashkan on 12/20/2017.
 */

public class OneRepMaxDataBaseHelper extends SQLiteOpenHelper {

    //simplifies constructor and might change so we use fields
    public static final String TABLE_NAME="oneRepMax.db";
    public static final int DATABASE_VERSION=3;

    public OneRepMaxDataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractClass.OneRepMaxEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.delete(ContractClass.OneRepMaxEntry.TABLE_NAME,null,null);
        onCreate(db);
    }
}
