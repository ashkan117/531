package com.example.ashkan.a531.Fragments;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ashkan.a531.Adapters.GraphRecycleViewAdapter;
import com.example.ashkan.a531.Data.DataManager;
import com.example.ashkan.a531.Data.OneRepMaxDataBaseHelper;
import com.example.ashkan.a531.Data.Week;
import com.example.ashkan.a531.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.handle;
import static android.media.CamcorderProfile.get;
import static com.example.ashkan.a531.Data.DataManager.getListOfWeeks;
import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.BENCH_PRESS;
import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.DEADLIFT;
import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.OHP;
import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.SQUAT;
import static java.util.Collections.sort;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment implements GraphPointDialogFragment.DialogListener{


    private FloatingActionButton fab;
    private int mWeekNumber;
    private String mExerciseType;
    private int mOneRepMax;
    private GraphView graph;
    private Context mContext;
    private LineGraphSeries<DataPoint> benchSeries;
    private Random mRand = new Random();
    private RecyclerView mRecyclerView;
    private ArrayList<Week> mListOfWeeks;
    private GraphRecycleViewAdapter recycleViewAdapter;
    private SQLiteDatabase db;
    private Button insertWeekButton;
    private GetWeekInformationFromSetFragment informationFromSetFragmentListener;
    private Week mWeekToInsert;
    private OneRepMaxDataBaseHelper helperDatabase;
    private EditText mWeekNumberEditText;
    private LineGraphSeries<DataPointInterface> squatSeries;
    private LineGraphSeries<DataPointInterface> deadLiftSeries;
    private LineGraphSeries<DataPointInterface> ohpSeries;
    private ArrayList<Week> allExercisesList;

    public GraphFragment() {
        // Required empty public constructor
    }


    public enum Exercise{
        BENCH_PRESS,
        SQUAT,
        DEADLIFT,
        OHP
    }

    public interface GetWeekInformationFromSetFragment{
        Week getWeekInfo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helperDatabase = new OneRepMaxDataBaseHelper(getContext());
         db = helperDatabase.getReadableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewGroup = (View) inflater.inflate(R.layout.fragment_graph,container,false);
        initViews(viewGroup);

        //TODO: tried using graph inside fragment but it wont let me
        //TODO: Useful way of getting support fragment from inside a fragment that is not support
        //FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
        //GraphFragmentPagerAdapter pagerAdapter = new GraphFragmentPagerAdapter(manager);

        initGraph();
        loadDataPoints();
        //handleSingularWeek();
        return viewGroup;
    }

    private void handleSingularWeek() {
        if(allExercisesList.size()==1){
            graph.removeAllSeries();
            //convertLineGraphToPoint();
        }
    }

    private void initViews(View viewGroup) {
        mContext =getContext();
        informationFromSetFragmentListener = (GetWeekInformationFromSetFragment) mContext;


        fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphPointDialogFragment customDialog = new GraphPointDialogFragment();
                //allows us to check inside the dialog if we came from this fragment
                customDialog.setTargetFragment(GraphFragment.this,1);
                customDialog.show(getFragmentManager(),"CustomDialog");
            }
        });
        mListOfWeeks = getListOfWeeks(helperDatabase);
        mWeekNumberEditText =(EditText) viewGroup.findViewById(R.id.week_number_insert_edit_text);
        insertWeekButton = (Button) viewGroup.findViewById(R.id.insert_week_button);
        insertWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekToInsert=informationFromSetFragmentListener.getWeekInfo();
                mWeekToInsert.setWeekNumber(Integer.parseInt(mWeekNumberEditText.getText().toString()));
                DataManager.addOneRepMax(mWeekToInsert,helperDatabase);
            }
        });
        graph = (GraphView) viewGroup.findViewById(R.id.graph);

        benchSeries = new LineGraphSeries<>();
        squatSeries = new LineGraphSeries<>();
        deadLiftSeries = new LineGraphSeries<>();
        ohpSeries = new LineGraphSeries<>();
    }

    private void loadDataPoints() {
        allExercisesList = DataManager.getListOfWeeks(helperDatabase);
        //Must sort DataPoints in asc order
        sortInASC(allExercisesList);
        //extracts the specific exercise from the total list into an array list of data points
        DataPoint[] squat = getExersise1RPMFromDatabase(SQUAT, allExercisesList);
        DataPoint[] bench = getExersise1RPMFromDatabase(BENCH_PRESS, allExercisesList);
        DataPoint[] deadlift = getExersise1RPMFromDatabase(DEADLIFT, allExercisesList);
        DataPoint[] ohp = getExersise1RPMFromDatabase(OHP, allExercisesList);
        //assigns those data points to a LineGraphSeries
        addDataPointToSeries(BENCH_PRESS,bench);
        addDataPointToSeries(SQUAT,squat);
        addDataPointToSeries(DEADLIFT,deadlift);
        addDataPointToSeries(OHP,ohp);
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

    private void addDataPointToSeries(Exercise whichExercise, DataPoint[] exercise) {
        switch(whichExercise){
            case BENCH_PRESS:
                for(int i=0;i<exercise.length;i++){
                    benchSeries.appendData(exercise[i],true,24);
                }
                break;
            case SQUAT:
                for(int i=0;i<exercise.length;i++){
                    squatSeries.appendData(exercise[i],true,24);
                }
                break;
            case DEADLIFT:
                for(int i=0;i<exercise.length;i++){
                    deadLiftSeries.appendData(exercise[i],true,24);
                }
                break;
            case OHP:
                for(int i=0;i<exercise.length;i++){
                    ohpSeries.appendData(exercise[i],true,24);
                }
                break;
        }
    }

    private DataPoint[] getExersise1RPMFromDatabase(Exercise whichExercise, ArrayList<Week> list) {
        //TODO: dataPoints must be sorted in ascending order

        //making it return a DataPoint[] since thats what the graph prefers (instead of arraylist)
        ArrayList<DataPoint> exercise = new ArrayList<>();
        DataPoint[] exerciseArray = new DataPoint[allExercisesList.size()];
        switch(whichExercise){
            case BENCH_PRESS:
                for(int i=0;i<list.size();i++){
                    int x=list.get(i).getWeekNumber();
                    int y=list.get(i).getBenchPress();
                    exerciseArray[i]=new DataPoint(x,y);
                }
                break;
            case SQUAT:
                for(int i=0;i<list.size();i++){
                    int x=list.get(i).getWeekNumber();
                    int y=list.get(i).getSquat();
                    exerciseArray[i]=new DataPoint(x,y);
                }
                break;
            case DEADLIFT:
                for(int i=0;i<list.size();i++){
                    int x=list.get(i).getWeekNumber();
                    int y=list.get(i).getDeadlift();
                    exerciseArray[i]=new DataPoint(x,y);
                }
                break;
            case OHP:
                for(int i=0;i<list.size();i++){
                    int x=list.get(i).getWeekNumber();
                    int y=list.get(i).getOhp();
                    exerciseArray[i]=new DataPoint(x,y);
                }
                break;
        }
        return exerciseArray;
    }


    private void initGraph() {
        //set some properties
        squatSeries.setColor(Color.RED);
        benchSeries.setColor(Color.GREEN);
        deadLiftSeries.setColor(Color.BLACK);
        ohpSeries.setColor(Color.BLUE);

        benchSeries.setTitle("Bench Press");
        squatSeries.setTitle("Squat");
        deadLiftSeries.setTitle("Deadlift");
        ohpSeries.setTitle("Overhead Press");

        benchSeries.setDrawDataPoints(true);
        squatSeries.setDrawDataPoints(true);
        deadLiftSeries.setDrawDataPoints(true);
        ohpSeries.setDrawDataPoints(true);

        graph.setTitle("Progress");

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        //set Scrollable and Scaleable
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        //set manual y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(500);
        graph.getViewport().setMinY(0);

        //set manual x bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(24);
        graph.getViewport().setMinX(0);

        //add the series to the graph
        graph.addSeries(benchSeries);
        graph.addSeries(squatSeries);
        graph.addSeries(deadLiftSeries);
        graph.addSeries(ohpSeries);
    }


    @Override
    public void returnInformationFromDialogToActivity(int weekNumber, String exerciseType, int oneRepMax) {
        mWeekNumber =weekNumber;
        mExerciseType =exerciseType;
        mOneRepMax =oneRepMax;
        addDataPoint();
    }

    private void addDataPoint() {
        //check if new week or not

        Week week = new Week(mWeekNumber,-1,-1,-1,-1);
        week.setExercise(mExerciseType,mOneRepMax,this);
        if(weekExists())
        {
            DataManager.updateExerciseEntry(week,helperDatabase);
        }
        else{
            DataManager.addOneRepMax(week,helperDatabase);
        }

        updateListAndSort(mExerciseType);
        updateGraph();

    }

    private void updateGraph() {
        graph.removeAllSeries();
        graph.addSeries(benchSeries);
        graph.addSeries(squatSeries);
        graph.addSeries(deadLiftSeries);
        graph.addSeries(ohpSeries);
    }

    private void updateListAndSort(String exercise) {
        allExercisesList = DataManager.getListOfWeeks(helperDatabase);
        sortInASC(allExercisesList);
        DataPoint[] dataPointArray = new DataPoint[allExercisesList.size()];
        switch (exercise){
            case "Bench Press":
                dataPointArray = getExersise1RPMFromDatabase(BENCH_PRESS,allExercisesList);
                benchSeries.resetData(dataPointArray);
                break;
            case "Squat":
                dataPointArray = getExersise1RPMFromDatabase(SQUAT,allExercisesList);
                squatSeries.resetData(dataPointArray);
                break;
            case "Deadlift":
                dataPointArray = getExersise1RPMFromDatabase(DEADLIFT,allExercisesList);
                deadLiftSeries.resetData(dataPointArray);
                break;
            case "Overhead Press":
                dataPointArray = getExersise1RPMFromDatabase(OHP,allExercisesList);
                ohpSeries.resetData(dataPointArray);
                break;

        }
    }

    private boolean weekExists() {
        Week week = new Week();
        week.setExercise(mExerciseType,mOneRepMax,this);
        week.setWeekNumber(mWeekNumber);
        return DataManager.weekExists(week,helperDatabase);
    }

}
