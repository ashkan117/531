package com.example.ashkan.a531.Data;

/**
 * Created by Ashkan on 2/5/2018.
 */

public class AlarmClockHolder {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private boolean mOnOff;
    private boolean mMondaySelected;
    private boolean mTuesdaySelected;
    private boolean mWednesdaySelected;
    private boolean mThursdaySelected;
    private boolean mFridaySelected;
    private boolean mSaturdaySelected;
    private boolean mSundaySelected;
    private int mPosition;

    public int getYear() {
        return mYear;
    }

    public void setOnOff(boolean onOff) {
        mOnOff = onOff;
    }

    public void setMondaySelected(boolean mondaySelected) {
        mMondaySelected = mondaySelected;
    }

    public void setTuesdaySelected(boolean tuesdaySelected) {
        mTuesdaySelected = tuesdaySelected;
    }

    public void setWednesdaySelected(boolean wednesdaySelected) {
        mWednesdaySelected = wednesdaySelected;
    }

    public void setThursdaySelected(boolean thursdaySelected) {
        mThursdaySelected = thursdaySelected;
    }

    public void setFridaySelected(boolean fridaySelected) {
        mFridaySelected = fridaySelected;
    }

    public void setSaturdaySelected(boolean saturdaySelected) {
        mSaturdaySelected = saturdaySelected;
    }

    public void setSundaySelected(boolean sundaySelected) {
        mSundaySelected = sundaySelected;
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

    public boolean isMondaySelected() {
        return mMondaySelected;
    }

    public void setMondaySelected(int mondaySelected) {
        mMondaySelected = mondaySelected==0?false:true;
    }

    public boolean isTuesdaySelected() {
        return mTuesdaySelected;
    }

    public void setTuesdaySelected(int tuesdaySelected) {
        mTuesdaySelected = tuesdaySelected==0?false:true;
    }

    public boolean isWednesdaySelected() {
        return mWednesdaySelected;
    }

    public void setWednesdaySelected(int wednesdaySelected) {
        mWednesdaySelected = wednesdaySelected==0?false:true;
    }

    public boolean isThursdaySelected() {
        return mThursdaySelected;
    }

    public void setThursdaySelected(int thursdaySelected) {
        mThursdaySelected = thursdaySelected==0?false:true;
    }

    public boolean isFridaySelected() {
        return mFridaySelected;
    }

    public void setFridaySelected(int fridaySelected) {
        mFridaySelected = fridaySelected==0?false:true;
    }

    public boolean isSaturdaySelected() {
        return mSaturdaySelected;
    }

    public void setSaturdaySelected(int saturdaySelected) {
        mSaturdaySelected = saturdaySelected==0?false:true;
    }

    public boolean isSundaySelected() {
        return mSundaySelected;
    }

    public void setSundaySelected(int sundaySelected) {
        mSundaySelected = sundaySelected==0?false:true;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPositionInDatabase() {
        return mPosition;
    }
}
