package com.example.ashkan.a531;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ashkan on 12/19/2017.
 */

public class SetsFragment extends android.support.v4.app.Fragment {

    private static String POSITION_OF_PAGER_KEY = "POSITION_OF_PAGER_KEY";
    private static String WEIGHT_LIFTED= "weightLifted";;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private int mPositionOfPager;
    private String mTypeOfExercise;
    private int mWeightLifted;

    //Avoid non default constructor. WIth the ways Fragmetns are initiaed it uses Bundle to restore states
    public static SetsFragment newInstance(int positionOfPager, int pounds) {
        SetsFragment fragment = new SetsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION_OF_PAGER_KEY,positionOfPager);
        bundle.putInt(WEIGHT_LIFTED,pounds);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean bundleSet=getArguments()!=null;
        //TODO:If we set paramaters using instance we check getArguments (I was wrongly checking savedInstanceState since it was a bundle)
        mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY,0);
        mWeightLifted = getArguments().getInt(WEIGHT_LIFTED,100);
        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_set,container,false);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.sets_recycler_view);
        //Recycler view needs adapter and layout
        setUpWhichExercise();
        setUpRecyclerView();
        initItems();
        return view;
    }

    private void initItems() {

    }

    private void setUpWhichExercise() {
        switch(mPositionOfPager){
            case 0:
                mTypeOfExercise ="Bench Press";
                break;
            case 1:
                mTypeOfExercise ="Squat";
                break;
            case 2:
                mTypeOfExercise ="Deadlift";
                break;
            case 3:
                mTypeOfExercise ="Overhead Press";
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
