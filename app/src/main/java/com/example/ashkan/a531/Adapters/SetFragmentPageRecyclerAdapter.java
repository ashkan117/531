package com.example.ashkan.a531.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashkan.a531.Interface.ISetFragmentPageRecyclerAdapter;
import com.example.ashkan.a531.R;
import com.example.ashkan.a531.databinding.SetsListItemBinding;

/**
 * Created by Ashkan on 12/20/2017.
 */


public class SetFragmentPageRecyclerAdapter extends RecyclerView.Adapter<SetFragmentPageRecyclerAdapter.CustomViewHolder>
    implements ISetFragmentPageRecyclerAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int mWeightLifted;
    private int mPositionOfPager;
    private int NUMBER_OF_SETS=9;
    private int[][] NUMBER_OF_REPS = new int[][]{
            {5,3,1,3,3,3,5,5,5},
            {5,3,1,3,5,3,5,3,5},
            {5,3,1,3,3,3,3,3,3},
            {5,3,1,3,3,3,5,5,5},
    };
    private int[] currentNumberOfRepsArray;

    private void setUpArray() {
        switch (mPositionOfPager){
            case 0:
                currentNumberOfRepsArray = NUMBER_OF_REPS[0];
                break;
            case 1:
                currentNumberOfRepsArray = NUMBER_OF_REPS[1];
                break;
            case 2:
                currentNumberOfRepsArray = NUMBER_OF_REPS[2];
                break;
            case 3 :
                currentNumberOfRepsArray = NUMBER_OF_REPS[3];
                break;
        }
    }

    public SetFragmentPageRecyclerAdapter(Context context, int positionOfPager, int weightLifted){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        mPositionOfPager=positionOfPager;
        mWeightLifted = weightLifted;
        setUpArray();
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
  //      View itemView = mLayoutInflater.inflate(R.layout.sets_list_item,parent,false);
        SetsListItemBinding setsListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.sets_list_item,parent,false);
        return new CustomViewHolder(setsListItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        int currentNumberOfReps = currentNumberOfRepsArray[position];
        holder.mSetsListItemBinding.setCurrentNumberOfReps(currentNumberOfReps);
//        holder.weight.setText(String.valueOf(mWeightLifted));
        holder.mSetsListItemBinding.setOneRepMax(mWeightLifted);
        holder.mSetsListItemBinding.setSetNumber(position + 1);
        int oneRepMax;
        switch (position){
            case 0:
                holder.mSetsListItemBinding.setPercentageOfWeight(75);
                oneRepMax= calculateOneRepMax(mWeightLifted,75);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);
                break;
            case 1:
                holder.mSetsListItemBinding.setPercentageOfWeight(85);
                oneRepMax= calculateOneRepMax(mWeightLifted,85);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);
                break;
            case 2:
                holder.mSetsListItemBinding.setPercentageOfWeight(95);
                oneRepMax= calculateOneRepMax(mWeightLifted,95);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);
                break;
            case 3:
                holder.mSetsListItemBinding.setPercentageOfWeight(90);
                oneRepMax= calculateOneRepMax(mWeightLifted,90);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);
                break;
            case 4:
                holder.mSetsListItemBinding.setPercentageOfWeight(85);
                oneRepMax= calculateOneRepMax(mWeightLifted,85);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);

                break;
            case 5:
                holder.mSetsListItemBinding.setPercentageOfWeight(80);
                oneRepMax= calculateOneRepMax(mWeightLifted,80);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);

                break;
            case 6:
                holder.mSetsListItemBinding.setPercentageOfWeight(75);
                oneRepMax= calculateOneRepMax(mWeightLifted,75);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);

                break;
            case 7:
                holder.mSetsListItemBinding.setPercentageOfWeight(70);
                oneRepMax= calculateOneRepMax(mWeightLifted,70);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);
                break;
            case 8:
                holder.mSetsListItemBinding.setPercentageOfWeight(65);
                oneRepMax= calculateOneRepMax(mWeightLifted,65);
                holder.mSetsListItemBinding.setOneRepMax(oneRepMax);
                break;
        }
    }

    private int calculateOneRepMax(int oneRepMax, double percentage) {
        //TODO:Careful for int division (75/100=0 with ints)
        double result=oneRepMax * (percentage/100);
        return Integer.parseInt(String.valueOf(5*(Math.round(result/5))));
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_SETS;
    }

    @Override
    public String addPlusToRepsIfNecessary(int setNumber, int oneRepMax, int numberOfReps) {
        if(setNumber == 3 || setNumber == 9){
            return  oneRepMax + " * " +numberOfReps + "+";
        }
        return  oneRepMax + " * " +numberOfReps;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private SetsListItemBinding mSetsListItemBinding;

        public CustomViewHolder(View itemView) {
            super(itemView);
            //TODO:WHy do we link the views here and not in onCreate???
            mSetsListItemBinding = DataBindingUtil.bind(itemView);
        }
    }


}