package com.example.ashkan.a531.Activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;



public class SettingsActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Needed to add theme for the settings to work
        //would throw error
        //fix was to add theme to the settings activity to the manifest
        super.onCreate(savedInstanceState);


    }
}
