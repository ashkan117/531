package com.example.ashkan.a531.Data.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ashkan.a531.Data.Alarm;
import com.example.ashkan.a531.Data.AlarmClockRepository;

import java.util.List;

/**
 * Created by Ashkan on 3/23/2018.
 */

public class AlarmClockViewModel extends AndroidViewModel {

    private final AlarmClockRepository mAlarmClockRepository;

    public AlarmClockViewModel(@NonNull Application application) {
        super(application);
        mAlarmClockRepository = new AlarmClockRepository(application);
    }

    public LiveData<List<Alarm>> getAllAlarms() {
        return mAlarmClockRepository.getAllAlarms();
    }

    public void removeClockAtPosition(Alarm alarm) {
        mAlarmClockRepository.removeClockAtPosition(alarm);
    }

    public void insertNewAlarmClock(Alarm alarm) {
        mAlarmClockRepository.insertNewAlarmClock(alarm);
    }
}
