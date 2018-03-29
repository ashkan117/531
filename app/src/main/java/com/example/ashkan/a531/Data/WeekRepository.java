package com.example.ashkan.a531.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.ashkan.a531.Model.Week;

import java.util.List;

/**
 * Created by Ashkan on 3/22/2018.
 */

public class WeekRepository {

    private WeekDao mWeekDao;

    public WeekRepository(Application application){
        OneRepMaxDatabase db = OneRepMaxDatabase.getDatabase(application);
        mWeekDao = db.WeekDao();
    }


    public void insertWeek(Week weekToInsert) {
        mWeekDao.insertWeek(weekToInsert);
    }

    public LiveData<List<Week>> getAllWeeks() {
        return mWeekDao.getAllListOfWeeks();
    }

    public void updateWeek(Week week) {
        mWeekDao.updateWeek(week);
    }

    public void deleteWeekWithWeekNumber(Week week) {
        mWeekDao.deleteWeekWithWeekNumber(week);
    }

    public void insertNewEmptyWeek(Week newWeek) {
        mWeekDao.insertNewEmptyWeek(newWeek);
    }

    public void updateWeekTable(List<Week> listOfWeeks) {
        mWeekDao.updateWeekTable(listOfWeeks);
    }
}
