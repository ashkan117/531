package com.example.ashkan.a531.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ashkan.a531.Data.ContractClass.AlarmClockEntry;
import com.example.ashkan.a531.Data.ContractClass.OneRepMaxEntry;

import java.util.ArrayList;

/**
 * Created by Ashkan on 12/28/2017.
 */

public class DataManager {

    private static DataManager ourInstance = null;

    public static DataManager getInstance()
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

    public static ContentValues contentValuesFromWeek(Week week) {
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

    public static ArrayList<Week> listOfWeeksFromCursor(Cursor cursor) {
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

    public static void deleteWeek(int weekNumber, OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER+" =? ";
        String whereArgs[] = new String[]{String.valueOf(weekNumber)};
        db.delete(OneRepMaxEntry.TABLE_NAME,where,whereArgs);
    }


    public static ArrayList<AlarmClockHolder> getListOfAlarmClockHolders(OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor table = db.query(ContractClass.AlarmClockEntry.TABLE_NAME,null,null,null,null,null,null);
        return cursorToAlarmClockHolder(table);
    }

    private static ArrayList<AlarmClockHolder> cursorToAlarmClockHolder(Cursor cursor) {
        ArrayList<AlarmClockHolder> holderArrayList = new ArrayList<AlarmClockHolder>();
        AlarmClockHolder currentHolder = new AlarmClockHolder();
        while (cursor.moveToNext()){
            int hour = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_HOUR);
            int minute = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_MINUTE);
            int position = cursor.getInt(AlarmClockEntry.COLUMN_INDEX_POSITION);
            int onOff = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_ON_OFF);
            int mondaySelected = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_MONDAY_SELECTED);
            int tuesdaySelected = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_TUESDAY_SELECTED);
            int wednesdaySelected = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_WEDNESDAY_SELECTED);
            int thursdaySelected = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_THURSDAY_SELECTED);
            int fridaySelected = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_FRIDAY_SELECTED);
            int saturdaySelected = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_SATURDAY_SELECTED);
            int sundaySelected = cursor.getInt(ContractClass.AlarmClockEntry.COLUMN_INDEX_SUNDAY_SELECTED);

            currentHolder.setHour(hour);
            currentHolder.setMinute(minute);
            currentHolder.setPosition(position);
            currentHolder.setOnOff(onOff);
            currentHolder.setMondaySelected (mondaySelected);
            currentHolder.setTuesdaySelected(tuesdaySelected);
            currentHolder.setWednesdaySelected(wednesdaySelected);
            currentHolder.setThursdaySelected(thursdaySelected);
            currentHolder.setFridaySelected(fridaySelected);
            currentHolder.setSaturdaySelected(saturdaySelected);
            currentHolder.setSundaySelected(sundaySelected);

            holderArrayList.add(currentHolder);
        }
        if(holderArrayList.size()==0){
            return null;
        }
        return holderArrayList;
    }

    public static int maintainClocks(AlarmClockHolder holder, OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = clockToContentValues(holder);
        String where = ContractClass.AlarmClockEntry.COLUMN_NAME_POSITION+" =? ";
        String[] whereArgs = new String[]{String.valueOf(holder.getPositionInDatabase())};
       // db.insert(AlarmClockEntry.TABLE_NAME,null,values);
       // return 0;
        return db.update(ContractClass.AlarmClockEntry.TABLE_NAME,values,where,whereArgs);
    }

    private static ContentValues clockToContentValues(AlarmClockHolder holder) {
        ContentValues values = new ContentValues();
        values.put(AlarmClockEntry.COLUMN_NAME_POSITION,holder.getPositionInDatabase());
        values.put(AlarmClockEntry.COLUMN_NAME_ON_OFF,holder.isOnOff());
        values.put(AlarmClockEntry.COLUMN_NAME_HOUR,holder.getHour());
        values.put(AlarmClockEntry.COLUMN_NAME_MINUTE,holder.getMinute());
        values.put(AlarmClockEntry.COLUMN_NAME_MONDAY_SELECTED,holder.isMondaySelected());
        values.put(AlarmClockEntry.COLUMN_NAME_TUESDAY_SELECTED,holder.isTuesdaySelected());
        values.put(AlarmClockEntry.COLUMN_NAME_WEDNESDAY_SELECTED,holder.isWednesdaySelected());
        values.put(AlarmClockEntry.COLUMN_NAME_THURSDAY_SELECTED,holder.isThursdaySelected());
        values.put(AlarmClockEntry.COLUMN_NAME_FRIDAY_SELECTED,holder.isFridaySelected());
        values.put(AlarmClockEntry.COLUMN_NAME_SATURDAY_SELECTED,holder.isSaturdaySelected());
        values.put(AlarmClockEntry.COLUMN_NAME_SUNDAY_SELECTED,holder.isSundaySelected());
        return values;
    }

    public static void insertNewAlarmClock(AlarmClockHolder holder, OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = clockToContentValues(holder);
        db.insert(AlarmClockEntry.TABLE_NAME,null,values);
    }

    public static void deleteAlarmClock(int position, OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = AlarmClockEntry.COLUMN_NAME_POSITION + " =? ";
        String[] whereArgs = new String[]{String.valueOf(position)};
        int success = db.delete(AlarmClockEntry.TABLE_NAME,where,whereArgs);
    }

    public int getWeekSize(OneRepMaxDataBaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columnArgs = new String[]{String.valueOf(OneRepMaxEntry.COLUMN_NAME_WEEK_NUMBER)};
        Cursor cursor = db.query(OneRepMaxEntry.TABLE_NAME,columnArgs,null,null,null,null,null);
        int maxWeekNumber = -1;
        int currentWeekNumber;
        while (cursor.moveToNext()){
            currentWeekNumber = cursor.getInt(OneRepMaxEntry.COLUMN_INDEX_WEEK_NUMBER);
            if(currentWeekNumber>maxWeekNumber){
                maxWeekNumber = currentWeekNumber;
            }
        }
        return maxWeekNumber;
    }


    public static void emptyClocks(OneRepMaxDataBaseHelper dbHelper) {
        if(!isClockEmpty(dbHelper)){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.delete(AlarmClockEntry.TABLE_NAME,null,null);
        }
    }

    private static boolean isClockEmpty(OneRepMaxDataBaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(AlarmClockEntry.TABLE_NAME,null,null,null,null,null,null);
        if(cursor==null&&cursor.getCount()==0){
            return true;
        }
        return false;
    }


}
