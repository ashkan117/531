package com.example.ashkan.a531.Data.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ashkan.a531.Data.WeekRepository;
import com.example.ashkan.a531.Model.Week;

import java.util.List;

/**
 * Created by Ashkan on 3/22/2018.
 */

public class WeekViewModel extends AndroidViewModel {

    private WeekRepository mWeekRepository;

    public WeekViewModel(@NonNull Application application) {
        super(application);
        mWeekRepository = new WeekRepository(application);
    }

    public void insertWeek(Week weekToInsert) {
        if(weekExists()){
            mWeekRepository.updateWeek(weekToInsert);
        }
        else{
            mWeekRepository.insertWeek(weekToInsert);
        }
    }

    private boolean weekExists() {
        List<Week> listOfWeeks = mWeekRepository.getAllWeeks().getValue();
        for(int i = 0; i< listOfWeeks.size(); i++){
            if(listOfWeeks.get(i).equals(listOfWeeks)){
                return true;
            }
        }
        return false;
    }

    public LiveData<List<Week>> getAllWeeks() {
       return mWeekRepository.getAllWeeks();
    }

    public void updateWeek(Week week) {
        mWeekRepository.updateWeek(week);
    }

    public void deleteWeekWithWeekNumber(Week week) {
        mWeekRepository.deleteWeekWithWeekNumber(week);
    }

    public void insertNewEmptyWeek(Week newWeek) {
        mWeekRepository.insertNewEmptyWeek(newWeek);
    }

    public void updateWeekTable(List<Week> listOfWeeks) {
        mWeekRepository.updateWeekTable(listOfWeeks);
    }
}
