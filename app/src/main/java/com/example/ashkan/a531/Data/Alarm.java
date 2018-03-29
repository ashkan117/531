package com.example.ashkan.a531.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Ashkan on 2/5/2018.
 */

@Entity(tableName = "alarm")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @TypeConverters({DateConverter.class})
    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "year")
    private int mYear;

    @ColumnInfo(name = "month")
    private int mMonth;

    @ColumnInfo(name = "day")
    private int mDay;

    @ColumnInfo(name = "hour")
    private int mHour;

    @ColumnInfo(name = "minute")
    private int mMinute;

    @ColumnInfo(name = "on_off_switch")
    private boolean mOnOff;

    @TypeConverters({DaysSelectedTypeConverter.class})
    @ColumnInfo(name = "days_selected")
    private boolean[] mDaysSelected;

    @ColumnInfo(name = "position_in_adapter")
    private int mPositionInAdapter;

    public int getPositionInAdapter() {
        return mPositionInAdapter;
    }

    public boolean[] getDaysSelected() {
        return mDaysSelected;
    }

    public void setDaysSelected(boolean[] daysSelected) {
        mDaysSelected = daysSelected;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }


    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getYear() {
        return mYear;
    }

    public void setOnOff(boolean onOff) {
        mOnOff = onOff;
    }


    public void setYear(int year) {
        mYear = year;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    public boolean isOnOff() {
        return mOnOff;
    }

    public void setOnOff(int onOff) {
        mOnOff = onOff==0?false:true;
    }

    public void setPositionInAdapter(int positionInAdapter) {
        mPositionInAdapter = positionInAdapter;
    }

    public int getPositionInDatabase() {
        return mPositionInAdapter;
    }
}
