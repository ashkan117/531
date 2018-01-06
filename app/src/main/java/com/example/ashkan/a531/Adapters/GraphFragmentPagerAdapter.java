package com.example.ashkan.a531.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ashkan.a531.Fragments.GraphFragment;
import com.example.ashkan.a531.Fragments.GraphFragmentItems;

import java.util.ArrayList;

/**
 * Created by Ashkan on 12/29/2017.
 */

public class GraphFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final int NUMBER_OF_GRAPHS=4;
    private ArrayList<GraphFragmentItems> listOfFragments= new ArrayList<>();

    public GraphFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_GRAPHS;
    }

    public void addFragment(int position, GraphFragmentItems items){
        listOfFragments.add(position,items);
    }
}
