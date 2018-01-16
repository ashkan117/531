package com.example.ashkan.a531.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashkan.a531.Data.Week;
import com.example.ashkan.a531.R;
import com.example.ashkan.a531.Util;

import java.util.ArrayList;

/**
 * Created by Ashkan on 12/29/2017.
 */

public class GraphRecycleViewAdapter extends RecyclerView.Adapter<GraphRecycleViewAdapter.GraphViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final ArrayList<Week> mListOfWeeks;
    private final Activity mActivity;
    private ArrayList<Week> mItemsChangedList;
    private EditTextListener mListener;
    private ArrayList<Week> mUpdatingList;


    public ArrayList<Week> requestData() {
        return null;
    }


    public interface EditTextListener{
        void updateWeek(Week updatedWeek);
        void setBeenFocused();
    }

    public GraphRecycleViewAdapter(Context context, ArrayList<Week> listOfWeeks, EditTextListener listener, Activity activity){
        mActivity = activity;
        mContext =context;
        mInflater = LayoutInflater.from(context);
        mListOfWeeks = listOfWeeks;
        mListener=(EditTextListener) listener;
        mUpdatingList=new ArrayList<Week>();
    }



    @Override
    public GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.table_item,parent,false);
        return new GraphViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(GraphViewHolder holder, int position) {
        //TODO: a possibly new list to handle updates
        //OR WE should be continuously updating the currentWeek with postition
        Week currentWeek = mListOfWeeks.get(position);
        holder.weekNumberTextView.setText("Week "+String.valueOf(currentWeek.getWeekNumber())+":");
        holder.benchPressMaxEditView.setText(String.valueOf(currentWeek.getBenchPress()));
        holder.squatMaxEditView.setText(String.valueOf(currentWeek.getSquat()));
        holder.deadliftMaxEditView.setText(String.valueOf(currentWeek.getDeadlift()));
        holder.ohpMaxEditView.setText(String.valueOf(currentWeek.getOhp()));
        if(!holder.benchPressMaxEditView.getText().toString().equals("TextView"))
        {
            initFocusListeners(holder,currentWeek, position);
        }
    }

    private void initFocusListeners(final GraphViewHolder holder, final Week week, int position) {
        holder.benchPressMaxEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    week.setBenchPress(Integer.parseInt(holder.benchPressMaxEditView.getText().toString()));
                    updateList(week);
                    mListener.updateWeek(week);
                    return true;
                }
                return false;
            }


        });
        holder.squatMaxEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    week.setSquat(Integer.parseInt(holder.squatMaxEditView.getText().toString()));
                    updateList(week);
                    mListener.updateWeek(week);
                    return true;
                }
                return false;
            }
        });
        holder.deadliftMaxEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    week.setDeadlift(Integer.parseInt(holder.deadliftMaxEditView.getText().toString()));
                    updateList(week);
                    mListener.updateWeek(week);
                    return true;
                }
                return false;
            }
        });
        holder.ohpMaxEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    week.setOhp(Integer.parseInt(holder.ohpMaxEditView.getText().toString()));
                    updateList(week);
                    mListener.updateWeek(week);
                    return true;
                }
                return false;
            }
        });
    }

    private void updateList(Week week) {
        for(int i=0;i<mListOfWeeks.size();i++){
            if(mListOfWeeks.get(i).getWeekNumber()==week.getWeekNumber()){
                mListOfWeeks.set(i,week);
                break;
            }
        }
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = mActivity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(mContext);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public int getItemCount() {
        return mListOfWeeks.size();
    }

    public class GraphViewHolder extends RecyclerView.ViewHolder{

        private final TextView weekNumberTextView;
        private final EditText benchPressMaxEditView;
        private final EditText squatMaxEditView;
        private final EditText deadliftMaxEditView;
        private final EditText ohpMaxEditView;

        public GraphViewHolder(View itemView) {
            super(itemView);
            weekNumberTextView = (TextView) itemView.findViewById(R.id.week_number_text_view);
            benchPressMaxEditView = (EditText) itemView.findViewById(R.id.bench_press_max_edit_text_view);
            squatMaxEditView = (EditText) itemView.findViewById(R.id.squat_max_edit_text_view);
            deadliftMaxEditView = (EditText) itemView.findViewById(R.id.deadlift_max_edit_text_view);
            ohpMaxEditView = (EditText) itemView.findViewById(R.id.ohp_max_edit_text_view);
        }
    }
}
