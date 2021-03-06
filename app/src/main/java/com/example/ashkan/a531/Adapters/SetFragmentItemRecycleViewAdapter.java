package com.example.ashkan.a531.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashkan.a531.R;

/**
 * Created by Ashkan on 12/20/2017.
 */


public class SetFragmentItemRecycleViewAdapter extends RecyclerView.Adapter<SetFragmentItemRecycleViewAdapter.CustomViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int mOneRepMax;
    private int mPositionOfPager;
    private int NUMBER_OF_SETS=9;
    private int[][] NUMBER_OF_REPS = new int[][]{
            {5,3,1,3,3,3,5,5,5},
            {5,3,1,3,5,3,5,3,5},
            {5,3,1,3,3,3,3,3,3},
            {5,3,1,3,3,3,5,5,5},
    };
    private int[] currentNumberOfReps;

    private void setUpArray() {
        switch (mPositionOfPager){
            case 0:
                currentNumberOfReps=NUMBER_OF_REPS[0];
                break;
            case 1:
                currentNumberOfReps=NUMBER_OF_REPS[1];
                break;
            case 2:
                currentNumberOfReps=NUMBER_OF_REPS[2];
                break;
            case 3 :
                currentNumberOfReps=NUMBER_OF_REPS[3];
                break;
        }
    }

    public SetFragmentItemRecycleViewAdapter(Context context, int positionOfPager, int weightLifted){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        mPositionOfPager=positionOfPager;
        mOneRepMax =weightLifted;
        setUpArray();
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.sets_list_item,parent,false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.weight.setText(String.valueOf(mOneRepMax));
        holder.setNumber.setText("Set "+String.valueOf(position+1));
        switch (position){
            case 0:
                holder.percentageOfWeight.setText("75%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,75)+" * "+currentNumberOfReps[position]);
                break;
            case 1:
                holder.percentageOfWeight.setText("85%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,85)+" * "+currentNumberOfReps[position]);
                break;
            case 2:
                holder.percentageOfWeight.setText("95%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,95)+" * "+currentNumberOfReps[position]+"+");
                break;
            case 3:
                holder.percentageOfWeight.setText("90%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,90)+" * "+currentNumberOfReps[position]);
                break;
            case 4:
                holder.percentageOfWeight.setText("85%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,85)+" * "+currentNumberOfReps[position]);
                break;
            case 5:
                holder.percentageOfWeight.setText("80%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,80)+" * "+currentNumberOfReps[position]);
                break;
            case 6:
                holder.percentageOfWeight.setText("75%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,75)+" * "+currentNumberOfReps[position]);
                break;
            case 7:
                holder.percentageOfWeight.setText("70%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,70)+" * "+currentNumberOfReps[position]);
                break;
            case 8:
                holder.percentageOfWeight.setText("65%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,65)+" * "+currentNumberOfReps[position]+"+");
                break;
        }
    }

    private String calculateOneRepMax(int oneRepMax, double percentage) {
        //TODO:Careful for int division (75/100=0 with ints)
        double result=oneRepMax*(percentage/100);
        return String.valueOf(5*(Math.round(result/5)));
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_SETS;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private final TextView percentageOfWeight;
        private final TextView setNumber;
        private final TextView weight;

        public CustomViewHolder(View itemView) {
            super(itemView);
            //TODO:WHy do we link the views here and not in onCreate???
            weight = (TextView) itemView.findViewById(R.id.weight_text_view);
            setNumber = (TextView) itemView.findViewById(R.id.set_number_text_view);
            percentageOfWeight = (TextView) itemView.findViewById(R.id.percentage_of_weight_text_view);
        }
    }


}