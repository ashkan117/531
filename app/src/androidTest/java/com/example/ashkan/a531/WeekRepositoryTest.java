package com.example.ashkan.a531;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.ashkan.a531.Data.OneRepMaxDatabase;
import com.example.ashkan.a531.Data.WeekDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class WeekRepositoryTest {

    private OneRepMaxDatabase mDb;
    private WeekDao mWeekDao;

//    @Mock
//    private Observer<List<Week>> mObserver;
//
    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        //Application application =
        mDb = Room.inMemoryDatabaseBuilder(context, OneRepMaxDatabase.class).build();
        mWeekDao = mDb.WeekDao();
        //mWeekRepository = new WeekRepository(context);
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void insertWeek(){
        CountDownLatch latch = new CountDownLatch(1);
    }
}
