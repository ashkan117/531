package com.example.ashkan.a531.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.ashkan.a531.Data.ContractClass.OneRepMaxEntry;

import java.util.ArrayList;

/**
 * Created by Ashkan on 12/28/2017.
 */

public class DataManager {

    private static DataManager ourInstance = null;

    public static DataManager getInstance(OneRepMaxDataBaseHelper db)
    {
        if(ourInstance==null)
        {
            ourInstance=new DataManager();
        }
        return ourInstance;
    }

    public static void addOneRepMax(Week week, OneRepMaxDataBaseHelper dbHelper)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(weekExists(week,dbHelper))
        {
            updateWeekEntry(week,dbHelper);
        }
        else{
            ContentValues contentValues = contentValuesFromWeek(week);
            db.insert(OneRepMaxEntry.TABLE_NAME,null,contentValues);
        }
    }

    public static int updateWeekEntry(Week week, OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = contentValuesFromWeek(week);
        String where = OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER+" =? ";
        String[] whereArgs = new String[]{String.valueOf(week.getWeekNumber())};
        //should return 1 (returns number of rows updated)
        return db.update(OneRepMaxEntry.TABLE_NAME,values,where,whereArgs);
    }

    public static boolean weekExists(Week week, OneRepMaxDataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(OneRepMaxEntry.TABLE_NAME,null,null,null,null,null,OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER);
        while (cursor.moveToNext()){
            if(week.getWeekNumber()==cursor.getInt(OneRepMaxEntry.COLUMN_INDEX_WEEK_NUMBER)){
                return true;
            }
        }
        return false;
    }

    private static ContentValues contentValuesFromWeek(Week week) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER,week.getWeekNumber());
        contentValues.put(OneRepMaxEntry.COLUMN_NAME_BENCH_PRESS,week.getBenchPress());
        contentValues.put(OneRepMaxEntry.COLUMN_NAME_SQUAT,week.getSquat());
        contentValues.put(OneRepMaxEntry.COLUMN_NAME_DEADLIFT,week.getDeadlift());
        contentValues.put(OneRepMaxEntry.COLUMN_NAME_OHP,week.getOhp());
        return contentValues;
    }

    public static ArrayList<Week> getListOfWeeks(OneRepMaxDataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query(OneRepMaxEntry.TABLE_NAME,null,null,null,null,null,null);
        return listOfWeeksFromCursor(cursor);
    }

    private static ArrayList<Week> listOfWeeksFromCursor(Cursor cursor) {
        ArrayList<Week> list = new ArrayList<>();
        while (cursor.moveToNext()){
            int weekNumber = cursor.getInt(OneRepMaxEntry.COLUMN_INDEX_WEEK_NUMBER);
            int bench = cursor.getInt(OneRepMaxEntry.COLUMN_INDEX_BENCH_PRESS);
            int squat = cursor.getInt(OneRepMaxEntry.COLUMN_INDEX_SQUAT);
            int deadlift = cursor.getInt(OneRepMaxEntry.COLUMN_INDEX_DEADLIFT);
            int ohp = cursor.getInt(OneRepMaxEntry.COLUMN_INDEX_OHP);
            Week week = new Week(weekNumber,bench,squat,deadlift,ohp);
            list.add(week);
        }
        return list;
    }

    public static void saveCurrentInput(ArrayList<Integer> listOf1RPM, OneRepMaxDataBaseHelper dbHelper) {
        Week week = new Week();
        week.setWeekNumber(-1);
        week.setBenchPress(listOf1RPM.get(0));
        week.setSquat(listOf1RPM.get(1));
        week.setDeadlift(listOf1RPM.get(2));
        week.setOhp(listOf1RPM.get(3));
        ContentValues values = contentValuesFromWeek(week);
        addOneRepMax(week,dbHelper);
    }

    public static void saveTabEditText(ArrayList<Integer> oneRepMaxList,OneRepMaxDataBaseHelper dbHelper) {

    }

    public static void updateExerciseEntry(Week week, OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER +" =? ";
        String[] whereArgs = new String[]{String.valueOf(week.getWeekNumber())};
        ContentValues values = new ContentValues();
        values.put(OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER,week.getWeekNumber());
        if(week.getBenchPress()!=-1){
            values.put(OneRepMaxEntry.COLUMN_NAME_BENCH_PRESS,week.getBenchPress());
        }
        else if(week.getSquat()!=-1){
            values.put(OneRepMaxEntry.COLUMN_NAME_SQUAT,week.getSquat());
        }
        else if(week.getDeadlift()!=-1){
            values.put(OneRepMaxEntry.COLUMN_NAME_DEADLIFT,week.getDeadlift());
        }
        else if(week.getOhp()!=-1){
            values.put(OneRepMaxEntry.COLUMN_NAME_OHP,week.getOhp());
        }
        db.update(OneRepMaxEntry.TABLE_NAME,values,where,whereArgs);
    }

    public static void saveNote(int currentItemPosition,OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

    }
}
