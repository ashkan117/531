package com.example.ashkan.a531.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.ashkan.a531.Fragments.GraphFragment;
import com.example.ashkan.a531.R;

/**
 * Created by Ashkan on 12/29/2017.
 */

@Entity(tableName = "week")
public class Week implements Parcelable{

    @ColumnInfo(name = "week_number")
    private int mWeekNumber;

    @ColumnInfo(name = "bench_press")
    private int mBenchPress;

    @ColumnInfo(name = "squat")
    private int mSquat;

    @ColumnInfo(name = "deadlift")
    private int mDeadlift;

    @ColumnInfo(name = "overhead_press")
    private int mOhp;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    public Week(){}

    //Can only have one valid constructor for Roonm
    @Ignore
    public Week(int weekNumber, int benchPress, int squat, int deadlift, int ohp){
        mWeekNumber =weekNumber;
        mBenchPress =benchPress;
        mSquat =squat;
        mDeadlift =deadlift;
        mOhp =ohp;
    }

    protected Week(Parcel in) {
        mWeekNumber = in.readInt();
        mBenchPress = in.readInt();
        mSquat = in.readInt();
        mDeadlift = in.readInt();
        mOhp = in.readInt();
    }

    public static final Creator<Week> CREATOR = new Creator<Week>() {
        @Override
        public Week createFromParcel(Parcel in) {
            return new Week(in);
        }

        @Override
        public Week[] newArray(int size) {
            return new Week[size];
        }
    };

        @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mWeekNumber);
        dest.writeInt(mBenchPress);
        dest.writeInt(mSquat);
        dest.writeInt(mDeadlift);
        dest.writeInt(mOhp);
    }

    public void setWeekNumber(int weekNumber){
        mWeekNumber=weekNumber;
    }

    public void setBenchPress(int mBenchPress) {
        this.mBenchPress = mBenchPress;
    }

    public void setSquat(int mSquat) {
        this.mSquat = mSquat;
    }

    public void setDeadlift(int mDeadlift) {
        this.mDeadlift = mDeadlift;
    }

    public void setOhp(int mOhp) {
        this.mOhp = mOhp;
    }

    public int getWeekNumber() {
        return mWeekNumber;
    }

    public int getBenchPress() {
        return mBenchPress;
    }

    public int getSquat() {
        return mSquat;
    }

    public int getDeadlift() {
        return mDeadlift;
    }

    public int getOhp() {
        return mOhp;
    }

    public void setExercise(String mExerciseType, int mOneRepMax, GraphFragment graphFragment) {;
        String[] spinnerEntries = graphFragment.getResources().getStringArray(R.array.exercises_string_array);
        if(mExerciseType.equals(spinnerEntries[0])){
            this.setBenchPress(mOneRepMax);
        }
        else if(mExerciseType.equals(spinnerEntries[1])){
            this.setSquat(mOneRepMax);
        }
        else if(mExerciseType.equals(spinnerEntries[2])){
            this.setDeadlift(mOneRepMax);
        }
        else if(mExerciseType.equals(spinnerEntries[3])){
            this.setOhp(mOneRepMax);
        }
        return;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

}
