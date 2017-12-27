package com.example.ashkan.a531;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.example.ashkan.a531.R.id.container;

/**
 * Created by Ashkan on 12/21/2017.
 */

public class MyFragmentPageAdapter extends FragmentStatePagerAdapter  {

    ArrayList<SetsFragment> listOfFragments=new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> listOfWeight= new ArrayList<>();

    public static final int NUMBER_OF_LIFTS = 3;


    interface OnTextChangedListener{
        int[] onWeightEntered (int positionOfPager, ArrayList<Integer> oneRepMaxList);
    }

    public void displayFragments(ArrayList<SetsFragment> list,int position)
    {
        for(int i=0;i<list.size();i++)
        {
            Log.v("FragmentPageAdapter","Fragment: "+list.get(position)
            +"Position: "+position);
        }
    }
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

        //POSITION_NONE
        //return super.getItemPosition(object);
        //FragmentPageAdapter issue
        //the reason this doesnt work is that notifycalls this function which just checks if the postition of our view item group changed
        //Since the view was replaced and there is something in that position then it thinks nothing changed
        //Nope: it returns the object at a position and it would make sense that if i changed the object there it would get that new item
        //TODO:CHanging to fragmentstateadapter and position none allows update
        //Moral of the story is only use fragmentadapter if we're using static fragments
            //due to the way the fragments saves information through cache it is easily confused when fragmetns are shuffled
            //if still an issue ad a unique id to each fragmetn and check if the ids match
            //this tells us if the fragment changed
        return POSITION_NONE;
    }

    public void addFragment(SetsFragment setsFragment) {
        listOfFragments.add(setsFragment);
    }


    public void replaceFragment(int position, ArrayList<Integer> oneRepMaxList) {

        displayFragments(listOfFragments,position);
        listOfFragments.set(position, SetsFragment.newInstance(position, oneRepMaxList));
        Log.v("FragmentAdapter", "" + oneRepMaxList.get(position)
        +"\n Position here is "+position);
        notifyDataSetChanged();
        displayFragments(listOfFragments,position);
    }
}
