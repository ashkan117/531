package com.example.ashkan.a531;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ashkan on 12/20/2017.
 */


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int mOneRepMax;
    private int mPositionOfPager;
    private int NUMBER_OF_SETS=9;

    public CustomAdapter(Context context, int positionOfPager, int weightLifted){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        mPositionOfPager=positionOfPager;
        mOneRepMax =weightLifted;
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
                holder.weight.setText(calculateOneRepMax(mOneRepMax,75));
                break;
            case 1:
                holder.percentageOfWeight.setText("85%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,85));
                break;
            case 2:
                holder.percentageOfWeight.setText("95%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,95));
                break;
            case 3:
                holder.percentageOfWeight.setText("90%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,90));
                break;
            case 4:
                holder.percentageOfWeight.setText("85%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,85));
                break;
            case 5:
                holder.percentageOfWeight.setText("80%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,80));
                break;
            case 6:
                holder.percentageOfWeight.setText("75%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,75));
                break;
            case 7:
                holder.percentageOfWeight.setText("70%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,70));
                break;
            case 8:
                holder.percentageOfWeight.setText("65%");
                holder.weight.setText(calculateOneRepMax(mOneRepMax,65));
                break;
        }
    }

    private String calculateOneRepMax(int oneRepMax, double percentage) {
        //TODO:Careful for int division (75/100=0 with ints)
        return String.valueOf(oneRepMax*(percentage/100));
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