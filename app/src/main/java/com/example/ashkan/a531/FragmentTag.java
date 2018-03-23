package com.example.ashkan.a531;

import android.support.v4.app.Fragment;

/**
 * Created by Ashkan on 2/26/2018.
 */

public class FragmentTag {
    public FragmentTag( Fragment fragment,String tag) {
        mTag = tag;
        mFragment = fragment;
    }

    public String getTag() {

        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    private String mTag;
    private Fragment mFragment;
}
