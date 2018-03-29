package com.example.ashkan.a531.Data;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by Ashkan on 3/23/2018.
 */

class DaysSelectedTypeConverter {


    //When we retrieve the primitive type from Room
    @TypeConverter
    public static boolean[] fromString(String daysSelectedString) {
        boolean[] daysSelected = new boolean[7];
        String[] daysSelectedStringArray = daysSelectedString.split(" ");
        for(int i = 0; i< daysSelected.length; i++){
            if(daysSelectedStringArray[i] == "true"){
                daysSelected[i] = true;
            }
            else {
                daysSelected[i] = false;
            }
        }
        return daysSelected;
    }

    //converts the Complex class into a primitive type that Room understands
    @TypeConverter
    public static String fromBooleanArray(boolean[] daysSelected) {
        String daysSelectedAsString = "";
        for (boolean bool : daysSelected) {
            if(bool){
                daysSelectedAsString += "true ";
            }
            else{
                daysSelectedAsString += "false";
            }
        }
        return daysSelectedAsString;
    }
}
