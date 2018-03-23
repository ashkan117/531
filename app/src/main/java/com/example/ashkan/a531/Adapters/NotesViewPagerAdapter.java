package com.example.ashkan.a531.Adapters;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;

import com.example.ashkan.a531.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;

/**
 * Created by Ashkan on 1/10/2018.
 */

public class NotesViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> listOfFragments = new ArrayList<>();


    public NotesViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listOfFragments.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    public void addFragment(int position,Fragment fragment){
        listOfFragments.add(position,fragment);
    }


}
