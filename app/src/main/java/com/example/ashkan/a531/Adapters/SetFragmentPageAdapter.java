package com.example.ashkan.a531.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashkan.a531.Fragments.SetFragmentItem;
import com.example.ashkan.a531.Fragments.SetsFragment;
import com.example.ashkan.a531.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Ashkan on 12/21/2017.
 */

public class SetFragmentPageAdapter extends FragmentStatePagerAdapter  {

    ArrayList<SetFragmentItem> listOfFragments=new ArrayList<SetFragmentItem>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> listOfWeight= new ArrayList<>();
    UpdateTabLayoutListener updateTabLayoutListener;
    public static final int NUMBER_OF_LIFTS = 3;
    private final Context mContext;
    private ArrayList<Integer> mOneRepMaxList;

    public interface UpdateTabLayoutListener {
        void updateTabLayoutPosition(int position, ArrayList<Integer> oneRepMaxList);
    }

    public interface OnTextChangedListener{
        int[] onWeightEntered (int positionOfPager, ArrayList<Integer> oneRepMaxList);
    }

    public void displayFragments(ArrayList<SetFragmentItem> list,int position)
    {
        for(int i=0;i<list.size();i++)
        {
            Log.v("FragmentPageAdapter","Fragment: "+list.get(position)
            +"Position: "+position);
        }
    }
    public SetFragmentPageAdapter(Context context, SetsFragment setsFragment, FragmentManager fm) {
        super(fm);
        mContext = context;
        updateTabLayoutListener = (UpdateTabLayoutListener) setsFragment;
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
        return POSITION_NONE;
    }

    public void addFragment(int position, SetFragmentItem setsFragmentItem) {
        //somehow the app is being recreated
        //the add causes the list to continuously add fragments
        if(listOfFragments.size()<=4){
            listOfFragments.add(setsFragmentItem);
        }
        else{
            listOfFragments.set(position,setsFragmentItem);
        }
    }

    public void replaceFragment(int position, ArrayList<Integer> oneRepMaxList) {
        //displayFragments(listOfFragments,position);
        listOfFragments.set(position, SetFragmentItem.newInstance(position, oneRepMaxList));
        mOneRepMaxList = new ArrayList<>();
        for (int i=0;i<oneRepMaxList.size();i++) {
            //mOneRepMaxList.set(i,oneRepMaxList.get(i));
            mOneRepMaxList.add(oneRepMaxList.get(i));
        }
//        updateTabLayoutListener.notifyDataSetChanged();
   //     updateTabLayoutListener.updateTabLayoutPosition(position,oneRepMaxList);
        //displayFragments(listOfFragments,position);
    }

}
