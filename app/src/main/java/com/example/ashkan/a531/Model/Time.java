package com.example.ashkan.a531.Model;

import android.os.Parcel;
import android.os.Parcelable;

import static com.example.ashkan.a531.Model.Time.AM_PM.AM;
import static com.example.ashkan.a531.Model.Time.AM_PM.PM;

/**
 * Created by Ashkan on 1/20/2018.
 */

public class Time implements Parcelable {
    public static final long ONE_HOUR_IN_MILLI = 60*60*1000;
    public static final long ONE_MINUTE_IN_MILLI = 60*1000;
    private int mHour;
    private int mMinute;
    private long mTimeInMilli;
    private boolean isAm;
    private AM_PM mAM_PM;
    public static long ONE_DAY_IN_MILLI=24*60*60*1000;

    public Time(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
        if(mHour<12){
            mAM_PM = AM;
        }
        else{
            mAM_PM = PM;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mHour);
        dest.writeInt(mMinute);
        dest.writeLong(mTimeInMilli);
        dest.writeInt(AM_PMInt());
    }

    public static final Parcelable.Creator<Time> CREATOR
            = new Parcelable.Creator<Time>() {
        public Time createFromParcel(Parcel in) {
            return new Time(in);
        }

        public Time[] newArray(int size) {
            return new Time[size];
        }
    };

    private Time(Parcel in) {
        mHour = in.readInt();
        mMinute = in.readInt();
        mTimeInMilli = in.readLong();
        setAM_PM(in.readInt());
    }

    private void setAM_PM(int i) {
        if(i==0){
            mAM_PM = AM;
        }
        else {
            mAM_PM = PM;
        }
    }

    private int AM_PMInt(){
        return mAM_PM==AM?0:1;
    }
    public enum AM_PM{
        AM,
        PM
    }

    public AM_PM getAM_PM() {
        return mAM_PM;
    }

    public void setAM_PM(AM_PM AM_PM) {
        mAM_PM = AM_PM;
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

    public Time()
    {

    }

    public long getTimeInMilli() {
        int hourInMilli = mHour * 60 *60 *1000;
        int minuteinMilli = getMinute()*60*1000;
        mTimeInMilli = hourInMilli+minuteinMilli;
        return mTimeInMilli;
    }


    public Time(int hour, int minute, AM_PM am_pm){
        mHour=hour;
        mMinute = minute;

        mAM_PM = am_pm;
    }

    public long differenceBetween(Time time){
        time.getTimeInMilli();
        return 0;
    }
}
