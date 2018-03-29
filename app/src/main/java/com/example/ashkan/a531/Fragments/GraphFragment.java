package com.example.ashkan.a531.Fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ashkan.a531.Adapters.TableRecycleViewAdapter;
import com.example.ashkan.a531.Data.ViewModel.WeekViewModel;
import com.example.ashkan.a531.Model.Week;
import com.example.ashkan.a531.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.BENCH_PRESS;
import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.DEADLIFT;
import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.OHP;
import static com.example.ashkan.a531.Fragments.GraphFragment.Exercise.SQUAT;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends android.support.v4.app.Fragment implements GraphPointDialogFragment.DialogListener
        ,TableFragment.UpdateGraphListener
        ,GraphPointDialogFragment.UpdateGraphFragmentListener{


    private FloatingActionButton fab;
    private int mWeekNumber;
    private String mExerciseType;
    private int mOneRepMax;
    private GraphView mGraphView;
    private Context mContext;
    private LineGraphSeries<DataPoint> benchSeries;
    private Random mRand = new Random();
    private RecyclerView mRecyclerView;
    private List<Week> mListOfWeeks;
    private LiveData<List<Week>> mListOfWeeksLiveData;
    private TableRecycleViewAdapter recycleViewAdapter;
    private SQLiteDatabase db;
    private Button insertWeekButton;
    private Week mWeekToInsert;
    private EditText mWeekNumberEditText;
    private LineGraphSeries<DataPointInterface> squatSeries;
    private LineGraphSeries<DataPointInterface> deadLiftSeries;
    private LineGraphSeries<DataPointInterface> ohpSeries;
    private ArrayList<Week> allExercisesList;
    private WeekViewModel mWeekViewModel;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void updateGraphAfterNewTable() {
        loadDataPoints();
    }


    public enum Exercise{
        BENCH_PRESS,
        SQUAT,
        DEADLIFT,
        OHP
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeekViewModel = ViewModelProviders.of(getActivity()).get(WeekViewModel.class);
        mListOfWeeks = mWeekViewModel.getAllWeeks().getValue();
        mWeekViewModel.getAllWeeks().observe(getActivity(), new Observer<List<Week>>() {
            @Override
            public void onChanged(@Nullable List<Week> weeks) {
                mListOfWeeks = sortInASC(weeks);
                loadDataPoints();
                initGraph();
                updateSeriesOfGraph();
            }
        });
    }

    private void updateSeriesOfGraph() {
        mGraphView.removeAllSeries();
        mGraphView.addSeries(benchSeries);
        mGraphView.addSeries(squatSeries);
        mGraphView.addSeries(deadLiftSeries);
        mGraphView.addSeries(ohpSeries);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewGroup = (View) inflater.inflate(R.layout.fragment_graph,container,false);
        initViews(viewGroup);

        //TODO: tried using mGraphView inside fragment but it wont let me
        //TODO: Useful way of getting support fragment from inside a fragment that is not support
        //FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
        //GraphFragmentPagerAdapter pagerAdapter = new GraphFragmentPagerAdapter(manager);

        loadDataPoints();
        initGraph();
        //handleSingularWeek();
        return viewGroup;
    }

    private void handleSingularWeek() {
        if(allExercisesList.size()==1){
            mGraphView.removeAllSeries();
            //convertLineGraphToPoint();
        }
    }

    private void initViews(View viewGroup) {
        mContext =getContext();
        //informationFromSetFragmentListener = (GetWeekInformationFromSetFragment) mContext;


        fab = (FloatingActionButton) viewGroup.findViewById(R.id.graph_fragment_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphPointDialogFragment customDialog = new GraphPointDialogFragment();
                //allows us to check inside the dialog if we came from this fragment
                customDialog.setTargetFragment(GraphFragment.this,1);
                customDialog.show(getFragmentManager(),"CustomDialog");
            }
        });

        mGraphView = (GraphView) viewGroup.findViewById(R.id.graph);

        benchSeries = new LineGraphSeries<>();
        squatSeries = new LineGraphSeries<>();
        deadLiftSeries = new LineGraphSeries<>();
        ohpSeries = new LineGraphSeries<>();
    }

    private void loadDataPoints() {
        //allExercisesList = DataManager.getListOfWeeks(helperDatabase);
        //Must sort DataPoints in asc order
        if(mListOfWeeks != null) {
            sortInASC(mListOfWeeks);
            //extracts the specific exercise from the total list into an array list of data points
            DataPoint[] squat = getExersise1RPMFromDatabase(SQUAT, mListOfWeeks);
            DataPoint[] bench = getExersise1RPMFromDatabase(BENCH_PRESS, mListOfWeeks);
            DataPoint[] deadlift = getExersise1RPMFromDatabase(DEADLIFT, mListOfWeeks);
            DataPoint[] ohp = getExersise1RPMFromDatabase(OHP, mListOfWeeks);
            //assigns those data points to a LineGraphSeries
            addDataPointToSeries(BENCH_PRESS, bench);
            addDataPointToSeries(SQUAT, squat);
            addDataPointToSeries(DEADLIFT, deadlift);
            addDataPointToSeries(OHP, ohp);
        }
        else{
            mListOfWeeks = new ArrayList<>();
        }
    }

    private List<Week> sortInASC(List<Week> list) {
        //TODO: IS this a possible reference issue?
        //Receive the data in asc or store it as asc so we dont need to sort it often
        for(int i=0;i<list.size()-1;i++){
            if(list.get(i).getWeekNumber()>list.get(i+1).getWeekNumber()){
                Week temp = list.get(i);
                list.set(i,list.get(i+1));
                list.set(i+1,temp);
            }
        }
        return list;
    }

    private void addDataPointToSeries(Exercise whichExercise, DataPoint[] exercise) {
        switch(whichExercise){
            case BENCH_PRESS:
                benchSeries.resetData(exercise);
//                for(int i=0;i<exercise.length;i++){
//                    benchSeries.appendData(exercise[i],true,24);
//                }
                break;
            case SQUAT:
                squatSeries.resetData(exercise);
//                for(int i=0;i<exercise.length;i++){
//                    squatSeries.appendData(exercise[i],true,24);
//                }
                break;
            case DEADLIFT:
                deadLiftSeries.resetData(exercise);
//                for(int i=0;i<exercise.length;i++){
//                    deadLiftSeries.appendData(exercise[i],true,24);
//                }
                break;
            case OHP:
                ohpSeries.resetData(exercise);
//                for(int i=0;i<exercise.length;i++){
//                    ohpSeries.appendData(exercise[i],true,24);
//                }
                break;
        }
    }

    private DataPoint[] getExersise1RPMFromDatabase(Exercise whichExercise, List<Week> list) {
        //TODO: dataPoints must be sorted in ascending order

        //making it return a DataPoint[] since thats what the mGraphView prefers (instead of arraylist)
        ArrayList<DataPoint> exercise = new ArrayList<>();
        DataPoint[] exerciseArray = new DataPoint[list.size()];
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

        //fixed/manual mGraphView
        if(mListOfWeeks.size()>0){
            setFixedGraph();
        }
        else{
            setScalableGraph();
        }
        //setFixedGraph();

        //scalable mGraphView

        //set some properties
        squatSeries.setColor(Color.RED);
        benchSeries.setColor(Color.GREEN);
        deadLiftSeries.setColor(Color.BLACK);
        ohpSeries.setColor(Color.BLUE);

        //sets title for the legend
        benchSeries.setTitle("Bench Press");
        squatSeries.setTitle("Squat");
        deadLiftSeries.setTitle("Deadlift");
        ohpSeries.setTitle("Overhead Press");

        benchSeries.setDrawDataPoints(true);
        squatSeries.setDrawDataPoints(true);
        deadLiftSeries.setDrawDataPoints(true);
        ohpSeries.setDrawDataPoints(true);

        mGraphView.setTitle("Progress");


        mGraphView.getGridLabelRenderer().setHumanRounding(true);

        mGraphView.getGridLabelRenderer().setHorizontalAxisTitle("Week");
        mGraphView.getGridLabelRenderer().setHorizontalAxisTitleTextSize(36);
        mGraphView.getGridLabelRenderer().setLabelHorizontalHeight(88);
        mGraphView.getGridLabelRenderer().setVerticalAxisTitle("Ib/Kgs");
        mGraphView.getGridLabelRenderer().setVerticalAxisTitleTextSize(36);

        mGraphView.getLegendRenderer().setVisible(true);
        mGraphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        //add the series to the mGraphView
        mGraphView.addSeries(benchSeries);
        mGraphView.addSeries(squatSeries);
        mGraphView.addSeries(deadLiftSeries);
        mGraphView.addSeries(ohpSeries);
    }

    private void setFixedGraph() {
        //set manual y bounds
        int maxY = getMaxY();
        int count = mListOfWeeks.size();
        int maxX = mListOfWeeks.get(count-1).getWeekNumber();
        mGraphView.getViewport().setYAxisBoundsManual(true);
        mGraphView.getViewport().setMaxY(maxY+(maxY*.6));
        mGraphView.getViewport().setMinY(0);

        //set manual x bounds
        mGraphView.getViewport().setXAxisBoundsManual(true);
        //so that points are not at edge
        mGraphView.getViewport().setMaxX(maxX+(maxX*.5));
        mGraphView.getViewport().setMinX(0);


    }

    private int getMaxY() {
        int currentBench;
        int currentDeadlift;
        int currentOhp;
        int max = mListOfWeeks.get(0).getSquat();
        for(int i=0;i<mListOfWeeks.size();i++){
            currentBench = mListOfWeeks.get(i).getBenchPress();
            currentDeadlift = mListOfWeeks.get(i).getDeadlift();
            currentOhp = mListOfWeeks.get(i).getOhp();
            if(max<mListOfWeeks.get(i).getBenchPress()){
                max = mListOfWeeks.get(i).getBenchPress();
            }
            else if(max<mListOfWeeks.get(i).getDeadlift()){
                max = mListOfWeeks.get(i).getDeadlift();
            }
            else if(max<mListOfWeeks.get(i).getOhp()){
                max = mListOfWeeks.get(i).getOhp();
            }
        }
        return max;
    }

    private void setScalableGraph() {
        // activate horizontal zooming and scrolling
        mGraphView.getViewport().setScalable(true);

        // activate horizontal scrolling
        mGraphView.getViewport().setScrollable(true);

        // activate horizontal and vertical zooming and scrolling
        mGraphView.getViewport().setScalableY(true);

        // activate vertical scrolling
        mGraphView.getViewport().setScrollableY(true);

    }


    @Override
    public void returnInformationFromDialogToActivity(int weekNumber, String exerciseType, int oneRepMax) {
        mWeekNumber = weekNumber;
        mExerciseType = exerciseType;
        mOneRepMax = oneRepMax;
        addDataPoint();
    }

    private void addDataPoint() {
        //check if new week or not

        Week week = new Week(mWeekNumber,-1,-1,-1,-1);
        week.setExercise(mExerciseType,mOneRepMax,this);

        mWeekViewModel.insertWeek(week);

        //updateGraph();

    }
    @Override
    public void updateGraph() {

        //Must sort DataPoints in asc order
        sortInASC(mListOfWeeks);
        //extracts the specific exercise from the total list into an array list of data points
        DataPoint[] squat = getExersise1RPMFromDatabase(SQUAT, mListOfWeeks);
        DataPoint[] bench = getExersise1RPMFromDatabase(BENCH_PRESS, mListOfWeeks);
        DataPoint[] deadlift = getExersise1RPMFromDatabase(DEADLIFT, mListOfWeeks);
        DataPoint[] ohp = getExersise1RPMFromDatabase(OHP, mListOfWeeks);
        benchSeries.resetData(bench);
        squatSeries.resetData(squat);
        deadLiftSeries.resetData(deadlift);
        ohpSeries.resetData(ohp);
    }
}
