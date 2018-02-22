package com.example.ashkan.a531.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ashkan on 12/20/2017.
 */

public final class ContractClass {
    //final since we dont want it to be inherited
    public static final String CONTENT_AUTHORITY = "com.example.ashkan.a531";
    public static final String PATH_ONE_REP_MAXS="oneRepMax";
    public static final String PATH_ALARM_CLOCKS="alarmClocks";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    private ContractClass(){} //private so that it cant be called
    public static final class OneRepMaxEntry implements BaseColumns {
        //content URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ONE_REP_MAXS);

        //table name
        public static final String TABLE_NAME="oneRepMax";
        //column name
        public final static String COLUMN_NAME_WEEK_NUMBER="weekNumber";
        public final static String COLUMN_NAME_BENCH_PRESS="benchPress";
        public final static String COLUMN_NAME_SQUAT="squat";
        public final static String COLUMN_NAME_DEADLIFT="deadlift";
        public final static String COLUMN_NAME_OHP="overHeadPress";
        //column index

        public static final int COLUMN_INDEX_ID=0;
        public static final int COLUMN_INDEX_WEEK_NUMBER=1;
        public final static int COLUMN_INDEX_BENCH_PRESS=2;
        public final static int COLUMN_INDEX_SQUAT=3;
        public final static int COLUMN_INDEX_DEADLIFT=4;
        public final static int COLUMN_INDEX_OHP=5;

        //CREATE table SQL command
        //CREATE oneRepMax (benchPress INTEGER, squat INTEGER, deadlift INTEGER, overHeadPress INTEGER);
        //Dont forget id
        //CREATE oneRepMax (_ID INTEGER PRIMARY KEY, benchPress INTEGER, squat INTEGER, deadlift INTEGER, overHeadPress INTEGER);
        public final static String CREATE_TABLE= "CREATE TABLE " + TABLE_NAME +" ( "
                +_ID +" INTEGER PRIMARY KEY, "
                +COLUMN_NAME_WEEK_NUMBER+" INTEGER, "
                + COLUMN_NAME_BENCH_PRESS+" INTEGER, "
                +COLUMN_NAME_SQUAT+" INTEGER, "
                +COLUMN_NAME_DEADLIFT+" INTEGER, "
                +COLUMN_NAME_OHP+" INTEGER );";
    }

    public static final class AlarmClockEntry implements BaseColumns {

        //content URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_ALARM_CLOCKS);

        //table name
        public static final String TABLE_NAME = "alarmClocks";

        //column names
        public static final String COLUMN_NAME_POSITION = "position";
        public static final String COLUMN_NAME_ON_OFF = "onOff";
        public static final String COLUMN_NAME_HOUR = "hour";
        public static final String COLUMN_NAME_MINUTE = "minute";
        public static final String COLUMN_NAME_MONDAY_SELECTED = "mondaySelected";
        public static final String COLUMN_NAME_TUESDAY_SELECTED = "tuesdaySelected";
        public static final String COLUMN_NAME_WEDNESDAY_SELECTED = "wednesdaySelected";
        public static final String COLUMN_NAME_THURSDAY_SELECTED = "thursdaySelected";
        public static final String COLUMN_NAME_FRIDAY_SELECTED = "fridaySelected";
        public static final String COLUMN_NAME_SATURDAY_SELECTED = "saturdaySelected";
        public static final String COLUMN_NAME_SUNDAY_SELECTED = "sundaySelected";

        //column indeces
        public static final int COLUMN_INDEX_ID = 0;
        public static final int COLUMN_INDEX_POSITION = 1;
        public static final int COLUMN_INDEX_ON_OFF = 2;
        public static final int COLUMN_INDEX_HOUR = 3;
        public static final int COLUMN_INDEX_MINUTE = 4;
        public static final int COLUMN_INDEX_MONDAY_SELECTED = 5;
        public static final int COLUMN_INDEX_TUESDAY_SELECTED = 6;
        public static final int COLUMN_INDEX_WEDNESDAY_SELECTED = 7;
        public static final int COLUMN_INDEX_THURSDAY_SELECTED = 8;
        public static final int COLUMN_INDEX_FRIDAY_SELECTED = 9;
        public static final int COLUMN_INDEX_SATURDAY_SELECTED = 10;
        public static final int COLUMN_INDEX_SUNDAY_SELECTED = 11;

        //Creating SQL Command

        public final static String CREATE_TABLE= "CREATE TABLE " + TABLE_NAME +" ( "
                +_ID +" INTEGER PRIMARY KEY, "
                +COLUMN_NAME_POSITION + " INTEGER, "
                +COLUMN_NAME_ON_OFF+" INTEGER, "
                + COLUMN_NAME_HOUR+" INTEGER, "
                +COLUMN_NAME_MINUTE+" INTEGER, "
                +COLUMN_NAME_MONDAY_SELECTED+" INTEGER, "
                +COLUMN_NAME_TUESDAY_SELECTED+" INTEGER,"
                +COLUMN_NAME_WEDNESDAY_SELECTED+" INTEGER, "
                +COLUMN_NAME_THURSDAY_SELECTED+" INTEGER,"
                +COLUMN_NAME_FRIDAY_SELECTED+" INTEGER, "
                +COLUMN_NAME_SATURDAY_SELECTED+" INTEGER,"
                +COLUMN_NAME_SUNDAY_SELECTED+" INTEGER );";
    }
}
