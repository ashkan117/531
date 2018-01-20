package com.example.ashkan.a531.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.ashkan.a531.Adapters.GraphRecycleViewAdapter;
import com.example.ashkan.a531.Data.DataManager;
import com.example.ashkan.a531.Data.OneRepMaxDataBaseHelper;
import com.example.ashkan.a531.Data.Week;
import com.example.ashkan.a531.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableFragment extends android.support.v4.app.Fragment implements GraphRecycleViewAdapter.EditTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private GraphRecycleViewAdapter recycleViewAdapter;
    private ArrayList<Week> mListOfWeeks;
    private OneRepMaxDataBaseHelper helperDatabase;
    private Context mContext;
    private Button updateButton;
    private boolean mBeenFocused=false;
    private LinearLayoutManager mLayoutManager;
    private UpdateGraphListener graphListener;

    public interface  UpdateGraphListener{
        void updateGraphAfterNewTable();
    }

    public TableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableFragment newInstance(String param1, String param2) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_table,menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getContext();
        helperDatabase = new OneRepMaxDataBaseHelper(mContext);
        mListOfWeeks = DataManager.getListOfWeeks(helperDatabase);
        sortInASC(mListOfWeeks);
    }


    private void sortInASC(ArrayList<Week> list) {
        //TODO: IS this a possible reference issue?
        //Receive the data in asc or store it as asc so we dont need to sort it often
        for(int i=0;i<list.size()-1;i++){
            if(list.get(i).getWeekNumber()>list.get(i+1).getWeekNumber()){
                Week temp = list.get(i);
                list.set(i,list.get(i+1));
                list.set(i+1,temp);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View viewGroup = (View) inflater.inflate(R.layout.fragment_table,container,false);
            mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.graph_recycler_view);
            recycleViewAdapter = new GraphRecycleViewAdapter(getContext(), mListOfWeeks,this,getActivity());
            mRecyclerView.setAdapter(recycleViewAdapter);
            mLayoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(mLayoutManager);
        //0 applies to up or down
            ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position =viewHolder.getAdapterPosition();
                    mListOfWeeks.remove(position);
                    deleteWeek(position);
                    recycleViewAdapter.notifyItemRemoved(position);
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);
            //make a simplecallback then
            //send this to the callback
            //attach this to recyclerview
            updateButton = (Button) viewGroup.findViewById(R.id.update_table_button);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Figure out a way to check if data has changed so we dont needlessly update the whole table
                    /*
                    //might work if i pass this fragment the information of the position of the adapter
                    mListOfWeeks=recycleViewAdapter.requestData();
                    int firstPostion =mLayoutManager.findFirstVisibleItemPosition();
                    int lastPosition = mLayoutManager.findLastVisibleItemPosition();
                    for(int i=firstPostion;i<lastPosition;i++){
                        SetFragmentItemRecycleViewAdapter.CustomViewHolder holder =
                                (SetFragmentItemRecycleViewAdapter.CustomViewHolder) mRecyclerView.findViewHolderForAdapterPosition(i);

                    }
                    */

                }


            });
            return viewGroup;
    }

    private void deleteWeek(int position) {
        int weekNumber = mListOfWeeks.get(position).getWeekNumber();
        DataManager.deleteWeek(weekNumber,helperDatabase);
    }

    @Override
    public void updateWeek(Week updatedWeek) {
        for(int i=0;i<mListOfWeeks.size();i++){
            Week currentWeek = mListOfWeeks.get(i);
            if(mListOfWeeks.get(i).getWeekNumber()==updatedWeek.getWeekNumber())
            {
                mListOfWeeks.set(i,updatedWeek);
                break;
            }
        }
        int updatedRows=DataManager.updateWeekEntry(updatedWeek,helperDatabase);

        //recycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBeenFocused() {
        mBeenFocused = true;
    }

    public void closeKeyBoard()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
