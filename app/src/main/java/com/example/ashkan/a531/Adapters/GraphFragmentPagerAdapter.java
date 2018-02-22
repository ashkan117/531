package com.example.ashkan.a531.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Ashkan on 12/29/2017.
 */

public class GraphFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final int NUMBER_OF_GRAPHS=4;

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

}
