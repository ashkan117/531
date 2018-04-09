package com.example.ashkan.a531.Data;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ashkan on 3/5/2018.
 *
 * This is mapping some complex data into primitives
 *
 * Lets say we had a class user with fields
 *  name
 *  age
 *
 *  We could save this as a string "Sam 18" that we can later retrieve and break apart
 */

public class DateConverter {
    static DateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy");


    //When we retrieve the primitive type from Room
    @TypeConverter
    public static Date toDate(long value) {
        try {
            Date date = new Date(value);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //converts the Complex class into a primitive type that Room understands
    @TypeConverter
    public static long fromDate(Date date) {
        if (date != null) {
            try {
                long aLong = date.getTime();
                return aLong;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        } else {
            return -1;
        }
    }
}
