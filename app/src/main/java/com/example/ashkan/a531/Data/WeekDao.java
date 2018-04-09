package com.example.ashkan.a531.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ashkan.a531.Model.Week;

import java.util.List;

/**
 * Created by Ashkan on 3/22/2018.
 */

//specify it's a variable with a colon
@Dao
public interface WeekDao {

    @Query("SELECT * FROM week")
    LiveData<List<Week>> getAllListOfWeeks();

    @Insert
    void insertWeek(Week weekToInsert);

    @Update
    void updateWeek(Week week);

    @Delete
    void deleteWeekWithWeekNumber(Week week);
    //void deleteWeekWithWeekNumber(List<Week> weeksList, int position);
    /*
    All of the parameters of the Delete method must either be classes annotated with Entity or collections/array of it.
    */

    @Insert
    void insertNewEmptyWeek(Week newWeek);

    @Update
    void updateWeekTable(List<Week> listOfWeeks);
}
