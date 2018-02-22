package com.example.ashkan.a531.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashkan.a531.Adapters.SetFragmentItemRecycleViewAdapter;
import com.example.ashkan.a531.R;

import java.util.ArrayList;

import static java.lang.Math.floor;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetFragmentItem extends android.support.v4.app.Fragment {


    private RecyclerView mRecyclerView;
    private int mPositionOfPager;
    private int mWeightLifted;
    private Context mContext;
    private ArrayList<Integer> mOneRepMaxList;
    private SetFragmentItemRecycleViewAdapter mCustomRecyclerViewAdapter;

    public static final String ONE_REP_MAX_LIST = "ONE_REP_MAX_LIST";
    private static String POSITION_OF_PAGER_KEY = "POSITION_OF_PAGER_KEY";
    private static String WEIGHT_LIFTED= "weightLifted";;
    private String mTypeOfExercise;

    public SetFragmentItem() {
        // Required empty public constructor
    }


    //Avoid non default constructor. WIth the ways Fragmetns are initiaed it uses Bundle to restore states
    public static SetFragmentItem newInstance(int positionOfPager, ArrayList<Integer> oneRepMaxList) {
        SetFragmentItem fragment = new SetFragmentItem();
        if(oneRepMaxList!=null&&oneRepMaxList.size()!=0) {
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
        outState.putIntegerArrayList(ONE_REP_MAX_LIST, mOneRepMaxList);
        outState.putInt(POSITION_OF_PAGER_KEY,mPositionOfPager);
        outState.putInt(WEIGHT_LIFTED,mOneRepMaxList.get(mPositionOfPager));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Log.v("Tag: ","1");
        mContext = getContext();

        if(savedInstanceState!=null)
        {
            mPositionOfPager = savedInstanceState.getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = savedInstanceState.getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList =savedInstanceState.getIntegerArrayList(ONE_REP_MAX_LIST);

        }
        else if(getArguments()!=null)
        {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList =getArguments().getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        //TODO:If we set paramaters using instance we check getArguments (I was wrongly checking savedInstanceState since it was a bundle)
        else
        {
            mPositionOfPager=0;
            mWeightLifted=0;
            mOneRepMaxList =new ArrayList<Integer>();
        }
        //setRetainInstance(true);
        //stateListener.repopulateTabs(mPositionOfPager,mOneRepMaxList);

        //Log.v("SetFragment","weightLifted: "+mWeightLifted +"\nPositionOfPager: "+mPositionOfPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_set_fragment_item, container, false);
        if(getArguments()!=null) {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY, 0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED, 99);
        }
        poundsOrKilogramsPreference();
        initViews(rootView);
        setUpWhichExercise();
        setUpRecyclerView();
        return rootView;
    }


    private void poundsOrKilogramsPreference() {
        //This one acesses the sttings sharedpreference vs one we'd make
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String key = getResources().getString(R.string.preference_key_pounds_kilogram);
        String value = sharedPreferences.getString(key,"Pounds");
        if(value.equals("Kilogram")){
            //convertToKiloGrams();
        }
    }

    private void convertToKiloGrams() {
        //2.20462ib = 1kg
        //1ib = 0.453592kg
        for(int i=0;i<mOneRepMaxList.size();i++){
            int pounds = mOneRepMaxList.get(i);
            int kilogram = (int) floor(pounds*0.453592);
            mOneRepMaxList.set(i,kilogram);
        }
        mWeightLifted *= 0.453592;
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
        SetFragmentItemRecycleViewAdapter adapter=new SetFragmentItemRecycleViewAdapter(mContext,mPositionOfPager,mWeightLifted);
        mRecyclerView.setAdapter(adapter);
    }

    private void initViews(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.sets_recycler_view);
        mCustomRecyclerViewAdapter = new SetFragmentItemRecycleViewAdapter(getContext(),mPositionOfPager,mWeightLifted);
    }

}
