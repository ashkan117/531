package com.example.ashkan.a531.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ashkan.a531.Model.Week;

/**
 * Created by Ashkan on 3/22/2018.
 */

@Database(entities = {Week.class,Alarm.class},version = 2, exportSchema = false)
public abstract class OneRepMaxDatabase extends RoomDatabase {


    public abstract WeekDao WeekDao();
    public abstract AlarmDao AlarmDao();

    private static OneRepMaxDatabase INSTANCE;


    static OneRepMaxDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WeekRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OneRepMaxDatabase.class, "one_rep_max")
                            //TODO must remove off mainthread
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
