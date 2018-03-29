package com.example.ashkan.a531.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.ashkan.a531.Activity.AlarmClockActivity;
import com.example.ashkan.a531.Adapters.TableRecycleViewAdapter;
import com.example.ashkan.a531.Data.ViewModel.WeekViewModel;
import com.example.ashkan.a531.Interface.TableInterface;
import com.example.ashkan.a531.Model.Week;
import com.example.ashkan.a531.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableFragment extends android.support.v4.app.Fragment implements TableRecycleViewAdapter.EditTextListener,
        TableFragmentDialog.TableFragmentDialogListener,
        TableInterface{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private TableRecycleViewAdapter mRecycleViewAdapter;
    private List<Week> mListOfWeeks;
    private Context mContext;
    private Button updateButton;
    private boolean mBeenFocused=false;
    private LinearLayoutManager mLayoutManager;
    private UpdateGraphListener graphListener;
    private FloatingActionButton fab;
    private WeekViewModel mWeekViewModel;

    @Override
    public void onPause() {
        super.onPause();
        mWeekViewModel.updateWeekTable(mListOfWeeks);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void insertNewWeek(int weekNumber) {
        Week newWeek = new Week(weekNumber,0,0,0,0);
        mWeekViewModel.insertNewEmptyWeek(newWeek);
    }

    @Override
    public void saveAllTables() {
        if(mListOfWeeks.size() > 0){
            for(int i = 0; i < mListOfWeeks.size(); i++){
            //    mRecycleViewAdapter;
                //TODO need to implement how to do this
            }
            mWeekViewModel.updateWeekTable(mListOfWeeks);
        }
    }
//todo monkey: adb shell monkey -p com.example.ashkan.a531 -v 1000
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.table_action_notifications:
                Intent intent = new Intent(getContext(), AlarmClockActivity.class);
                startActivity(intent);
                return true;
            case R.id.table_action_settings:
                //TODO: FINISH
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mWeekViewModel = ViewModelProviders.of(getActivity()).get(WeekViewModel.class);
        mListOfWeeks = mWeekViewModel.getAllWeeks().getValue();
        if(mListOfWeeks != null){
            sortInASC(mListOfWeeks);
        }
        mWeekViewModel.getAllWeeks().observe(getActivity(), new Observer<List<Week>>() {
            @Override
            public void onChanged(@Nullable List<Week> weeks) {
                mListOfWeeks = sortInASC(weeks);
                mRecycleViewAdapter.switchList(mListOfWeeks);;
            }
        });
    }

    private List<Week> sortInASC(List<Week> list) {
        //TODO: How to sort an arraylist of objects (not sure why this method was needed)
        //Receive the data in asc or store it as asc so we dont need to sort it often

        Collections.sort(list, new Comparator<Week>() {
                    @Override
                    public int compare(Week o1, Week o2) {
                        if (o1.getWeekNumber() > o2.getWeekNumber()) {
                            return 1;
                        } else if (o1.getWeekNumber() == o2.getWeekNumber()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View viewGroup = (View) inflater.inflate(R.layout.fragment_table,container,false);
             setUpRecyclerView(viewGroup);
            setUpViews(viewGroup);
            return viewGroup;
    }

    private void setUpViews(View viewGroup) {
        updateButton = (Button) viewGroup.findViewById(R.id.update_table_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Figure out a way to check if data has changed so we dont needlessly update the whole table
                    /*
                    //might work if i pass this fragment the information of the position of the adapter
                    mListOfWeeks=mRecycleViewAdapter.requestData();
                    int firstPostion =mLayoutManager.findFirstVisibleItemPosition();
                    int lastPosition = mLayoutManager.findLastVisibleItemPosition();
                    for(int i=firstPostion;i<lastPosition;i++){
                        SetFragmentItemRecycleViewAdapter.CustomViewHolder holder =
                                (SetFragmentItemRecycleViewAdapter.CustomViewHolder) mRecyclerView.findViewHolderForAdapterPosition(i);

                    }
                    */

            }


        });

        fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab_table_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createDialog();
//                int weekNumber = mListOfWeeks.size();
//                Week newWeek = new Week(weekNumber,0,0,0,0);
//                //Adding new week
//                ContentValues values = contentValuesFromWeek(newWeek);
//                Uri uri = mContectResolver.insert(ContractClass.OneRepMaxEntry.CONTENT_URI,values);
//                long id = ContentUris.parseId(uri);
//                //setting the Id
//                if(id!=-1){
//                    newWeek.setId(id);
//                }//save item in list
//                mListOfWeeks.add(newWeek);
//                mRecycleViewAdapter.notifyItemInserted(weekNumber);
            }
        });
    }

    private void createDialog() {
        TableFragmentDialog tableFragmentDialog = TableFragmentDialog.newInstance(mListOfWeeks.size()+1);
        tableFragmentDialog.setTargetFragment(TableFragment.this,2);
        tableFragmentDialog.show(getFragmentManager(),"TableFragmentDialog");
    }


    private void setUpRecyclerView(View viewGroup) {
        mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.table_recycler_view);
        mRecycleViewAdapter = new TableRecycleViewAdapter(getContext(), mListOfWeeks,this,getActivity());
        mRecyclerView.setAdapter(mRecycleViewAdapter);
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
                deleteWeekWithWeekNumber(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        //make a simplecallback then
        //send this to the callback
        //attach this to recyclerview
    }

    private void deleteWeekWithWeekNumber(int position) {
        int weekNumber = mListOfWeeks.get(position).getWeekNumber();
        Week weekToDelete = mListOfWeeks.get(position);
        mWeekViewModel.deleteWeekWithWeekNumber(weekToDelete);
    }

    /***
     *
     * @param updatedWeek
     *
     * Once the graph enters a week that exists it updates the week in the table
     */
    @Override
    public void updateWeek(Week updatedWeek) {
        mWeekViewModel.updateWeek(updatedWeek);
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
