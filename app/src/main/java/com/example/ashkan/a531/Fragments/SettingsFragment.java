package com.example.ashkan.a531.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.example.ashkan.a531.R;

/**
 * Created by Ashkan on 2/19/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat {


    /**
     * PreferenceFragmentCompat vs PreferenceFragment
     * PreferenceFragment came first, is based on a listview
     * PreferenceFragmentCompat is more flexible is based off recyclerview
     *      ex: preferences are displaced in recyclerview
     * */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings,rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String string = getResources().getString(R.string.preference_key_pounds_kilogram);
        String test = sharedPreferences.getString(string,"Pounds");
    }
}
