package com.example.ashkan.a531;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static java.lang.reflect.Array.getInt;

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

    //Avoid non default constructor. WIth the ways Fragmetns are initiaed it uses Bundle to restore states
    public static SetsFragment newInstance(int positionOfPager, ArrayList<Integer> oneRepMaxList) {
        SetsFragment fragment = new SetsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION_OF_PAGER_KEY,positionOfPager);
        bundle.putInt(WEIGHT_LIFTED,oneRepMaxList.get(positionOfPager));
        bundle.putIntegerArrayList(ONE_REP_MAX_LIST, oneRepMaxList);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getContext();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getArguments()!=null)
        {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=getArguments().getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        if(outState.isEmpty())
        {
            outState.putIntegerArrayList(ONE_REP_MAX_LIST, mOneRepMaxList);
            outState.putInt(POSITION_OF_PAGER_KEY,mPositionOfPager);
            outState.putInt(WEIGHT_LIFTED,mOneRepMaxList.get(mPositionOfPager));
        }



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Log.v("SetFragment","weightLifted: "+mWeightLifted +"\nPositionOfPager: "+mPositionOfPager);
        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_set,container,false);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.sets_recycler_view);
        //Recycler view needs adapter and layout
        if(getArguments()!=null) {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY, 0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED, 99);
        }
        setUpWhichExercise();
        setUpRecyclerView();
        initItems();
        return view;
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
