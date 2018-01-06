package com.example.ashkan.a531.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashkan.a531.Adapters.CustomAdapter;
import com.example.ashkan.a531.R;

import java.util.ArrayList;

import static android.R.id.edit;

/**
 * Created by Ashkan on 12/19/2017.
 */

public class SetsFragment extends android.support.v4.app.Fragment {

    public static final String ONE_REP_MAX_LIST = "ONE_REP_MAX_LIST";
    private static String POSITION_OF_PAGER_KEY = "POSITION_OF_PAGER_KEY";
    private static String WEIGHT_LIFTED= "weightLifted";;
    private static ArrayList<Integer> mOneRepMaxList;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private int mPositionOfPager;
    private String mTypeOfExercise;
    private int mWeightLifted;
    private OnSetsFragmentRecoveredStateListener stateListener;


    public interface OnSetsFragmentRecoveredStateListener{
        void repopulateTabs(int position,ArrayList<Integer> oneRepMaxList);
    }

    //Avoid non default constructor. WIth the ways Fragmetns are initiaed it uses Bundle to restore states
    public static SetsFragment newInstance(int positionOfPager, ArrayList<Integer> oneRepMaxList) {
        SetsFragment fragment = new SetsFragment();
        if(oneRepMaxList!=null) {
            Bundle bundle = new Bundle();
            bundle.putInt(POSITION_OF_PAGER_KEY, positionOfPager);
            bundle.putInt(WEIGHT_LIFTED, oneRepMaxList.get(positionOfPager));
            bundle.putIntegerArrayList(ONE_REP_MAX_LIST, oneRepMaxList);
            fragment.setArguments(bundle);
        }
        return fragment ;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("Tag: ","4");
        if(getArguments()!=null)
        {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=getArguments().getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        outState.putIntegerArrayList(ONE_REP_MAX_LIST, mOneRepMaxList);
        outState.putInt(POSITION_OF_PAGER_KEY, mPositionOfPager);
        outState.putInt(WEIGHT_LIFTED, mOneRepMaxList.get(mPositionOfPager));
    }

   //TODO: onsaveREstored not right to restore text in edittext


    @Override
    public void onResume() {
        super.onResume();
        //stateListener.repopulateTabs(mPositionOfPager,mOneRepMaxList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("Tag: ","3");
        if(savedInstanceState!=null)
        {
            mPositionOfPager = savedInstanceState.getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = savedInstanceState.getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=savedInstanceState.getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        //setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Tag: ","1");
        mContext = getContext();
        stateListener = (OnSetsFragmentRecoveredStateListener) mContext;
        if(savedInstanceState!=null)
        {
            mPositionOfPager = savedInstanceState.getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = savedInstanceState.getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=savedInstanceState.getIntegerArrayList(ONE_REP_MAX_LIST);

        }
        else if(getArguments()!=null)
        {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=getArguments().getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        //TODO:If we set paramaters using instance we check getArguments (I was wrongly checking savedInstanceState since it was a bundle)
        else
        {
            mPositionOfPager=0;
            mWeightLifted=0;
            mOneRepMaxList=new ArrayList<Integer>();
        }
        //setRetainInstance(true);
        //stateListener.repopulateTabs(mPositionOfPager,mOneRepMaxList);

        Log.v("SetFragment","weightLifted: "+mWeightLifted +"\nPositionOfPager: "+mPositionOfPager);
        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_set,container,false);
        Log.v("Tag: ","2");
        mRecyclerView=(RecyclerView) view.findViewById(R.id.sets_recycler_view);
        //Recycler view needs adapter and layout
        if(getArguments()!=null) {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY, 0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED, 99);
        }
        setUpWhichExercise();
        setUpRecyclerView();
        setUpCustomTabView();
        initItems();
        return view;
    }

    private void setUpCustomTabView() {
        //TabLayout tabLayout =
    }

    private void initItems() {

    }

    private void setUpWhichExercise() {
        switch(mPositionOfPager){
            case 1:
                mTypeOfExercise ="Squat";
                break;
            case 2:
                mTypeOfExercise ="Deadlift";
                break;
            case 3:
                mTypeOfExercise ="Overhead Press";
                break;
            default:
                mTypeOfExercise ="Bench Press";
                break;

        }
    }

    private void setUpRecyclerView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter adapter=new CustomAdapter(mContext,mPositionOfPager,mWeightLifted);
        mRecyclerView.setAdapter(adapter);
    }


}
