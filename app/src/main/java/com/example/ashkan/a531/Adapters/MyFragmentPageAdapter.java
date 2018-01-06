package com.example.ashkan.a531.Adapters;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashkan.a531.Fragments.SetsFragment;
import com.example.ashkan.a531.R;

import java.util.ArrayList;

/**
 * Created by Ashkan on 12/21/2017.
 */

public class MyFragmentPageAdapter extends FragmentStatePagerAdapter  {

    private final TabLayout mTabLayout;
    ArrayList<SetsFragment> listOfFragments=new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> listOfWeight= new ArrayList<>();
    UpdateTabLayout updateTabLayoutListener;
    public static final int NUMBER_OF_LIFTS = 3;
    private final Context mContext;
    private ArrayList<Integer> mOneRepMaxList;

    public interface UpdateTabLayout{
        void updateTabLayout(int position, ArrayList<Integer> oneRepMaxList);
    }

    public interface OnTextChangedListener{
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
    public MyFragmentPageAdapter(UpdateTabLayout mainScreen, FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        mContext = (Context) mainScreen;
        updateTabLayoutListener = mainScreen;
        mTabLayout = tabLayout;
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
            //if still an issue ad a unique id to each fragment and check if the ids match
            //this tells us if the fragment changed
        return POSITION_NONE;
    }

    public void addFragment(SetsFragment setsFragment) {
        listOfFragments.add(setsFragment);
    }

    /*
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 1:
                return "Squat";
            case 2:
                return "Deadlift";
            case 3:
                return "Overhead Press";
            default:
                return "Bench Press";
        }
    }
    */



    public View getTabView(int position,ArrayList<Integer> oneRepMaxList) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab_item, null);
        TextView tv = (TextView) v.findViewById(R.id.type_of_exercise_text_view);
        EditText editText = (EditText) v.findViewById(R.id.weight_edit_text_view);
        switch (position){
            case 0:
                tv.setText("Bench Press");
                break;
            case 1:
                tv.setText("Squat");
                break;
            case 2:
                tv.setText("Deadlift");
                break;
            case 3:
                tv.setText("Overhead Press");
                break;
        }
        editText.setText(oneRepMaxList.get(position).toString());
        return v;
    }


    public void replaceFragment(int position, ArrayList<Integer> oneRepMaxList) {


        displayFragments(listOfFragments,position);
        listOfFragments.set(position, SetsFragment.newInstance(position, oneRepMaxList));
        Log.v("FragmentAdapter", "" + oneRepMaxList.get(position)
        +"\n Position here is "+position);
        mOneRepMaxList = new ArrayList<>();
        for (int i=0;i<oneRepMaxList.size();i++) {
            //mOneRepMaxList.set(i,oneRepMaxList.get(i));
            mOneRepMaxList.add(oneRepMaxList.get(i));
        }
        notifyDataSetChanged();
        updateTabLayoutListener.updateTabLayout(position,oneRepMaxList);
        displayFragments(listOfFragments,position);
    }

}
