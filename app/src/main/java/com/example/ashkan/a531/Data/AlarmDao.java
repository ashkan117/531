package com.example.ashkan.a531.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Ashkan on 3/23/2018.
 */
@Dao
public interface AlarmDao {

    @Insert
    void insertAlarm(Alarm alarm);

    @Query("SELECT * FROM alarm")
    LiveData<List<Alarm>> getAllAlarms();

    @Delete
    void removeClockAtPosition(Alarm alarm);

    @Insert
    void insertNewAlarmClock(Alarm alarm);
}
