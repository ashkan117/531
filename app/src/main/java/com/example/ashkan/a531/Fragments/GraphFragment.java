package com.example.ashkan.a531.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashkan.a531.Data.ViewModel.WeekViewModel;
import com.example.ashkan.a531.DialogFragments.GraphPointDialogFragment;
import com.example.ashkan.a531.Model.Week;
import com.example.ashkan.a531.R;
import com.example.ashkan.a531.databinding.FragmentGraphBinding;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;

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
    private Context mContext;
    private List<Week> mListOfWeeks;
    private LineGraphSeries<DataPointInterface> benchLineSeries;
    private LineGraphSeries<DataPointInterface> squatLineSeries;
    private LineGraphSeries<DataPointInterface> deadLiftLineSeries;
    private LineGraphSeries<DataPointInterface> ohpLineSeries;
    private WeekViewModel mWeekViewModel;
    private FragmentGraphBinding mFragmentGraphBinding;
    private final int mMatrixSize = 4;
    private PointsGraphSeries<DataPointInterface> mPointSeries;
    private final int mNumberOfExercises = 4;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void updateGraphAfterNewTable() {
        initLineSeries();
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
                /*
                Issue was the observer is done off the main thread
                The list of weeks was null from the getAllWeeks().getValue()
                Then later on the onChanged was called
                initLineSeries() resets the series to be their updated version
                All is good and fine but the issue is we never added the series to the graphs in the first place
                ResetSeries is okay for the graph if the graph is already aware of the series
                */
                initGraph();
            }
        });
    }

    private void initGraph() {
        initGraphProperties();
        initLineSeries();
        initLineSeriesProperties();
        initLegend();
    }

    private void initLegend() {
        mFragmentGraphBinding.graph.getLegendRenderer().setVisible(true);
        mFragmentGraphBinding.graph.getLegendRenderer().setTextSize(36);
        mFragmentGraphBinding.graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    private void initLineSeriesProperties() {

        //This sets for Legend
        squatLineSeries.setColor(Color.RED);
        benchLineSeries.setColor(Color.GREEN);
        deadLiftLineSeries.setColor(Color.BLACK);
        ohpLineSeries.setColor(Color.BLUE);


        // custom paint to make a dotted line
        makeLineDotted(benchLineSeries,Color.GREEN);
        makeLineDotted(squatLineSeries,Color.RED);
        makeLineDotted(deadLiftLineSeries,Color.BLACK);
        makeLineDotted(ohpLineSeries,Color.BLUE);

        //sets title for the legend
        benchLineSeries.setTitle("Bench Press");
        squatLineSeries.setTitle("Squat");
        deadLiftLineSeries.setTitle("Deadlift");
        ohpLineSeries.setTitle("Overhead Press");
    }

    private void makeLineDotted(LineGraphSeries<DataPointInterface> lineSeries, int color) {
        lineSeries.setDataPointsRadius(10);
        lineSeries.setThickness(8);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(color);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));


        lineSeries.setDrawDataPoints(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentGraphBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph,container,false);
        mContext = getContext();
        initListeners();

        benchLineSeries = new LineGraphSeries<>();
        squatLineSeries = new LineGraphSeries<>();
        deadLiftLineSeries = new LineGraphSeries<>();
        ohpLineSeries = new LineGraphSeries<>();

        if(mListOfWeeks != null){
            initGraph();
        }
        return mFragmentGraphBinding.getRoot();
    }

    private void addLineSeriesToGraph() {
        mFragmentGraphBinding.graph.addSeries(benchLineSeries);
        mFragmentGraphBinding.graph.addSeries(squatLineSeries);
        mFragmentGraphBinding.graph.addSeries(deadLiftLineSeries);
        mFragmentGraphBinding.graph.addSeries(ohpLineSeries);
    }

    private void initListeners() {
        mFragmentGraphBinding.graphFragmentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphPointDialogFragment customDialog = new GraphPointDialogFragment();
                //allows us to check inside the dialog if we came from this fragment
                customDialog.setTargetFragment(GraphFragment.this,1);
                customDialog.show(getFragmentManager(),"CustomDialog");
            }
        });
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


    private void initGraphProperties() {
        boolean isVisible = mFragmentGraphBinding.graph.getLegendRenderer().isVisible();

        if(mListOfWeeks == null || isVisible){
            return;
        }
        //fixed/manual mGraphView
        if(mListOfWeeks.size() > 0){
            setGraphDimensions();
        }
        //set some properties
        mFragmentGraphBinding.graph.setTitle("Progress");
        mFragmentGraphBinding.graph.getGridLabelRenderer().setHumanRounding(true);
        //mFragmentGraphBinding.graph.getGridLabelRenderer().setLabelsSpace(8);

        mFragmentGraphBinding.graph.getGridLabelRenderer().setHorizontalAxisTitle("Week");
        mFragmentGraphBinding.graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(42);
        mFragmentGraphBinding.graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.BLUE);
        //mFragmentGraphBinding.graph.getGridLabelRenderer().setLabelHorizontalHeight(24);

        mFragmentGraphBinding.graph.getGridLabelRenderer().setVerticalAxisTitle("Ib/Kgs");
        mFragmentGraphBinding.graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(42);
        mFragmentGraphBinding.graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLUE);
        //mFragmentGraphBinding.graph.getGridLabelRenderer().setLabelVerticalWidth(24);
    }

    private void setGraphDimensions() {
        //set manual y bounds
        int maxY = getMaxY();
        int count = mListOfWeeks.size();
        int maxX = mListOfWeeks.get(count - 1).getWeekNumber();

        mFragmentGraphBinding.graph.getViewport().setYAxisBoundsManual(true);
        mFragmentGraphBinding.graph.getViewport().setMaxY(maxY+(maxY*.6));
        mFragmentGraphBinding.graph.getViewport().setMinY(0);

        //set manual x bounds
        mFragmentGraphBinding.graph.getViewport().setXAxisBoundsManual(true);
        //so that points are not at edge
        mFragmentGraphBinding.graph.getViewport().setMaxX(maxX+(maxX*.5));
        mFragmentGraphBinding.graph.getViewport().setMinX(0);

        // enable scaling and scrolling
        setScalableGraph();
    }

    private int getMaxY() {
        int currentBench;
        int currentDeadlift;
        int currentOhp;
        int max = mListOfWeeks.get(0).getSquat();
        for(int i=0; i < mListOfWeeks.size(); i++){
            currentBench = mListOfWeeks.get(i).getBenchPress();
            currentDeadlift = mListOfWeeks.get(i).getDeadlift();
            currentOhp = mListOfWeeks.get(i).getOhp();
            if(max < mListOfWeeks.get(i).getBenchPress()){
                max = mListOfWeeks.get(i).getBenchPress();
            }
            else if(max < mListOfWeeks.get(i).getDeadlift()){
                max = mListOfWeeks.get(i).getDeadlift();
            }
            else if(max < mListOfWeeks.get(i).getOhp()){
                max = mListOfWeeks.get(i).getOhp();
            }
        }
        return max;
    }

    private void setScalableGraph() {
        mFragmentGraphBinding.graph.getViewport().setScrollable(true); // enables horizontal scrolling
        mFragmentGraphBinding.graph.getViewport().setScrollableY(false); // enables vertical scrolling
        mFragmentGraphBinding.graph.getViewport().setScalable(false); // enables horizontal zooming and scrolling
        mFragmentGraphBinding.graph.getViewport().setScalableY(false); // enables vertical zooming and scrolling
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

    }
    @Override
    public void initLineSeries() {
        List<Series> attachedSeries = mFragmentGraphBinding.graph.getSeries();
        if(mListOfWeeks != null && mListOfWeeks.size()!=0){
            //Must sort DataPoints in asc order
            sortInASC(mListOfWeeks);
            //extracts the specific exercise from the total list into an array list of data points
            DataPoint[] squat = getExersise1RPMFromDatabase(SQUAT, mListOfWeeks);
            DataPoint[] bench = getExersise1RPMFromDatabase(BENCH_PRESS, mListOfWeeks);
            DataPoint[] deadlift = getExersise1RPMFromDatabase(DEADLIFT, mListOfWeeks);
            DataPoint[] ohp = getExersise1RPMFromDatabase(OHP, mListOfWeeks);
            benchLineSeries.resetData(bench);
            squatLineSeries.resetData(squat);
            deadLiftLineSeries.resetData(deadlift);
            ohpLineSeries.resetData(ohp);
            if(attachedSeries.isEmpty()){
                mFragmentGraphBinding.graph.addSeries(benchLineSeries);
                mFragmentGraphBinding.graph.addSeries(squatLineSeries);
                mFragmentGraphBinding.graph.addSeries(deadLiftLineSeries);
                mFragmentGraphBinding.graph.addSeries(ohpLineSeries);
            }
        }
    }
}
