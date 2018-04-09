package com.example.ashkan.a531.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Ashkan on 3/23/2018.
 */

public class AlarmClockRepository {

    private AlarmDao mAlarmDao;

    public AlarmClockRepository(Application application) {
        OneRepMaxDatabase db = OneRepMaxDatabase.getDatabase(application);
        mAlarmDao = db.AlarmDao();
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return mAlarmDao.getAllAlarms();
    }

    public void removeClockAtPosition(Alarm alarm) {
        mAlarmDao.removeClockAtPosition(alarm);
    }

    public void insertNewAlarmClock(Alarm alarm) {
        mAlarmDao.insertNewAlarmClock(alarm);
    }
}
