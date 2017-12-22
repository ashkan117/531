package com.example.ashkan.a531;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashkan on 12/21/2017.
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    ArrayList<SetsFragment> listOfFragments=new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> listOfWeight= new ArrayList<>();

    public static final int NUMBER_OF_LIFTS = 3;

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listOfFragments.get(position);
    }

    @Override
    public int getCount() {
        return listOfFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {

        return super.getItemPosition(object);
    }

    public void addFragment(SetsFragment setsFragment, String typeOfExercise, int pounds) {
        listOfFragments.add(setsFragment);
        title.add(typeOfExercise);
        listOfWeight.add(pounds);
    }
}
