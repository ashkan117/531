package com.example.ashkan.a531;

import android.provider.BaseColumns;

/**
 * Created by Ashkan on 12/20/2017.
 */

public final class ContractClass {
    //final since we dont want it to be inherited
    private ContractClass(){} //private so that it cant be called
    public static final class OneRepMaxEntry implements BaseColumns {
        //table name
        public static final String TABLE_NAME="oneRepMax";
        //column name

        public final static String COLUMN_NAME_BENCH_PRESS="benchPress";
        public final static String COLUMN_NAME_SQUAT="squat";
        public final static String COLUMN_NAME_DEADLIFT="deadlift";
        public final static String COLUMN_NAME_OHP="overHeadPress";
        //column index

        public static final int COLUMN_INDEX_ID=0;
        public final static int COLUMN_INDEX_BENCH_PRESS=1;
        public final static int COLUMN_INDEX_SQUAT=2;
        public final static int COLUMN_INDEX_DEADLIFT=3;
        public final static int COLUMN_INDEX_OHP=4;

        //CREATE table SQL command
        //CREATE oneRepMax (benchPress INTEGER, squat INTEGER, deadlift INTEGER, overHeadPress INTEGER);
        //Dont forget id
        //CREATE oneRepMax (_ID INTEGER PRIMARY KEY, benchPress INTEGER, squat INTEGER, deadlift INTEGER, overHeadPress INTEGER);
        public final static String CREATE_TABLE= "CREATE " + TABLE_NAME +" ( "
                +_ID +" INTEGER PRIMARY KEY "
                + COLUMN_NAME_BENCH_PRESS+" INTEGER, "
                +COLUMN_NAME_SQUAT+" INTEGER, "
                +COLUMN_NAME_DEADLIFT+" INTEGER, "
                +COLUMN_NAME_OHP+" INTEGER );";
    }
}
