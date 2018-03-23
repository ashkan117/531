package com.example.ashkan.a531.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ashkan.a531.Model.Week;

import static com.example.ashkan.a531.Data.DataManager.contentValuesFromWeek;
import static com.example.ashkan.a531.Data.DataManager.updateWeekEntry;

/**
 * Created by Ashkan on 2/14/2018.
 */

public class OneRepMaxContentProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Uri code
    //we need 2 codes for each table
    //  1 to handle the case if we want to manage the entire table (ex: delete all)
    //  2 to handle the case if we want to manage an indiviual item of the list (ex: update item 2)

    private static final int ONE_REP_MAX = 0;
    private static final int ONE_REP_MAX_ID = 1;
    private static final int ALARM_CLOCK = 2;
    private static final int ALARM_CLOCK_ID = 3;

    //initializes only once???
    static {
        mUriMatcher.addURI(ContractClass.CONTENT_AUTHORITY,ContractClass.PATH_ONE_REP_MAXS,ONE_REP_MAX);
        mUriMatcher.addURI(ContractClass.CONTENT_AUTHORITY,ContractClass.PATH_ONE_REP_MAXS+"/#",ONE_REP_MAX_ID);

        mUriMatcher.addURI(ContractClass.CONTENT_AUTHORITY,ContractClass.PATH_ALARM_CLOCKS,ALARM_CLOCK);
        mUriMatcher.addURI(ContractClass.CONTENT_AUTHORITY,ContractClass.PATH_ALARM_CLOCKS+"/#",ALARM_CLOCK_ID);
    }

    private OneRepMaxDataBaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new OneRepMaxDataBaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String orderBy) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int code = mUriMatcher.match(uri);
        switch (code){
            case ONE_REP_MAX:
                cursor = queryOneRepMaxTable(db);
                break;
            case ONE_REP_MAX_ID:
                cursor = queryOneRepMaxTableElement(db,projection,selection,selectionArgs,orderBy);
                break;
            case ALARM_CLOCK:
                cursor = queryAlarmClockTable(db);
                break;
            case ALARM_CLOCK_ID:
                cursor = queryAlarmClockTableElement(db,projection,selection,selectionArgs,orderBy);
        }
        return cursor;
    }

    private Cursor queryAlarmClockTableElement(SQLiteDatabase db, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        return db.query(ContractClass.AlarmClockEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,orderBy);
    }

    private Cursor queryAlarmClockTable(SQLiteDatabase db) {
        return db.query(ContractClass.AlarmClockEntry.TABLE_NAME,null,null,null,null,null,null);
    }

    private Cursor queryOneRepMaxTableElement(SQLiteDatabase db, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        return db.query(ContractClass.OneRepMaxEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,orderBy);
    }

    private Cursor queryOneRepMaxTable(SQLiteDatabase db) {
        return db.query(ContractClass.OneRepMaxEntry.TABLE_NAME,null,null,null,null,null,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = mUriMatcher.match(uri);
        long id=-1;
        switch (code){
            case ONE_REP_MAX:
                id = insertNewWeek(values);
                break;
            case ALARM_CLOCK:
                //id = insertNewAlarm();
                break;
        }
        return ContentUris.withAppendedId(uri,id);
    }

    private long insertNewWeek(ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(ContractClass.OneRepMaxEntry.TABLE_NAME,null,values);
    }


    public long addOneRepMax(Week week, OneRepMaxDataBaseHelper dbHelper)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(weekExists(week,dbHelper))
        {
            updateWeekEntry(week,dbHelper);
        }
        else{
            ContentValues contentValues = contentValuesFromWeek(week);
            return db.insert(ContractClass.OneRepMaxEntry.TABLE_NAME,null,contentValues);
        }
        return -1;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int code = mUriMatcher.match(uri);
        switch (code){
            case ONE_REP_MAX:
                return deleteAllOneRepMaxEntries(db);
            case ONE_REP_MAX_ID:
                return deleteOneRepMaxEntry(db,selection,selectionArgs);

            case ALARM_CLOCK:
                return deleteAlarmClockEntries(db);
            case ALARM_CLOCK_ID:
                return deleteAlarmClockEntry(db,selection,selectionArgs);
        }
        return 0;
    }

    private int deleteAlarmClockEntry(SQLiteDatabase db, String selection, String[] selectionArgs) {
        return db.delete(ContractClass.AlarmClockEntry.TABLE_NAME,selection,selectionArgs);
    }

    private int deleteAlarmClockEntries(SQLiteDatabase db) {
        return db.delete(ContractClass.AlarmClockEntry.TABLE_NAME,null,null);
    }

    private int deleteOneRepMaxEntry(SQLiteDatabase db, String selection, String[] selectionArgs) {
        return db.delete(ContractClass.OneRepMaxEntry.TABLE_NAME,selection,selectionArgs);
    }

    private int deleteAllOneRepMaxEntries(SQLiteDatabase db) {
        return db.delete(ContractClass.OneRepMaxEntry.TABLE_NAME,null,null);
    }


    //Not good practice because to update a single entry i should send the single entry uri
    //I dont do that since i didnt have an id as a element for my weeks
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int code = mUriMatcher.match(uri);
        switch (code){
            case ONE_REP_MAX:
                return updateOneRepMaxEntry(db,values,selection,selectionArgs);
            case ONE_REP_MAX_ID:
                return updateOneRepMaxEntry(db,values,selection,selectionArgs);
            case ALARM_CLOCK:
                return updateAlarmClockEntry(db,values,selection,selectionArgs);
            case ALARM_CLOCK_ID:
                return updateAlarmClockEntry(db,values,selection,selectionArgs);
        }
        return 0;
    }

    private int updateAlarmClockEntry(SQLiteDatabase db, ContentValues values, String selection, String[] selectionArgs) {
        return db.update(ContractClass.AlarmClockEntry.TABLE_NAME,values,selection,selectionArgs);
    }

    private int updateOneRepMaxEntry(SQLiteDatabase db, ContentValues values, String selection, String[] selectionArgs) {
        return db.update(ContractClass.OneRepMaxEntry.TABLE_NAME,values,selection,selectionArgs);
    }

    public static boolean weekExists(Week week, OneRepMaxDataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ContractClass.OneRepMaxEntry.TABLE_NAME,null,null,null,null,null, ContractClass.OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER);
        while (cursor.moveToNext()){
            if(week.getWeekNumber()==cursor.getInt(ContractClass.OneRepMaxEntry.COLUMN_INDEX_WEEK_NUMBER)){
                return true;
            }
        }
        return false;
    }
}
