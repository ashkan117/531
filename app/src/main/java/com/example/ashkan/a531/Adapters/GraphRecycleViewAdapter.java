package com.example.ashkan.a531.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashkan.a531.Data.Week;
import com.example.ashkan.a531.R;

import java.util.ArrayList;

import static android.R.id.list;

/**
 * Created by Ashkan on 12/29/2017.
 */

public class GraphRecycleViewAdapter extends RecyclerView.Adapter<GraphRecycleViewAdapter.GraphViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final ArrayList<Week> mListOfWeeks;

    public GraphRecycleViewAdapter(Context context, ArrayList<Week> listOfWeeks){
        mContext =context;
        mInflater = LayoutInflater.from(context);
        mListOfWeeks = listOfWeeks;
    }



    @Override
    public GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.table_item,parent,false);
        return new GraphViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(GraphViewHolder holder, int position) {
        holder.weekNumberTextView.setText("Week "+String.valueOf(mListOfWeeks.get(position).getWeekNumber())+":");
        holder.benchPressMaxTextView.setText(String.valueOf(mListOfWeeks.get(position).getBenchPress()));
        holder.squatMaxTextView.setText(String.valueOf(mListOfWeeks.get(position).getSquat()));
        holder.deadliftMaxTextView.setText(String.valueOf(mListOfWeeks.get(position).getDeadlift()));
        holder.ohpMaxTextView.setText(String.valueOf(mListOfWeeks.get(position).getOhp()));
    }

    @Override
    public int getItemCount() {
        return mListOfWeeks.size();
    }

    public class GraphViewHolder extends RecyclerView.ViewHolder{

        private final TextView weekNumberTextView;
        private final TextView benchPressMaxTextView;
        private final TextView squatMaxTextView;
        private final TextView deadliftMaxTextView;
        private final TextView ohpMaxTextView;

        public GraphViewHolder(View itemView) {
            super(itemView);
            weekNumberTextView = (TextView) itemView.findViewById(R.id.week_number_text_view);
            benchPressMaxTextView = (TextView) itemView.findViewById(R.id.bench_press_max_text_view);
            squatMaxTextView = (TextView) itemView.findViewById(R.id.squat_max_text_view);
            deadliftMaxTextView = (TextView) itemView.findViewById(R.id.deadlift_max_text_view);
            ohpMaxTextView = (TextView) itemView.findViewById(R.id.ohp_max_text_view);
        }
    }
}
