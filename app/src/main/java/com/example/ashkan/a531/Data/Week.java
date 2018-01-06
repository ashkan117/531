package com.example.ashkan.a531.Data;

import com.example.ashkan.a531.Fragments.GraphFragment;
import com.example.ashkan.a531.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ashkan on 12/29/2017.
 */

public class Week {

    private int mWeekNumber;
    private int mBenchPress;
    private int mSquat;
    private int mDeadlift;
    private int mOhp;

    public Week(){}

    public Week(int weekNumber, int benchPress, int squat, int deadlift, int ohp){
        mWeekNumber =weekNumber;
        mBenchPress =benchPress;
        mSquat =squat;
        mDeadlift =deadlift;
        mOhp =ohp;
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
}
