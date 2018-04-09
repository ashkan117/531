package com.example.ashkan.a531.databinding;

import com.example.ashkan.a531.Model.Week;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.List;

/**
 * Created by Ashkan on 3/30/2018.
 */


public class GraphSeriesAdapter {

    private static PointsGraphSeries<DataPointInterface> benchPointSeries;
    private static PointsGraphSeries<DataPointInterface> squatPointSeries;
    private static PointsGraphSeries<DataPointInterface> deadliftPointSeries;
    private static PointsGraphSeries<DataPointInterface> ohpPointSeries;

    //Needs to be static
  //  @BindingAdapter({"app:listOfWeeks","app:numberOfExercises"})
    public static void setPointGraphSeries(GraphView graphView, List<Week> listOfWeeks, int numberOfExercises){
        //If this isn't set when the function is called it gets the default values
        if(listOfWeeks == null){
            return;
        }
        int sizeOfList = listOfWeeks.size();
        benchPointSeries = new PointsGraphSeries<>();
        squatPointSeries = new PointsGraphSeries<>();
        deadliftPointSeries = new PointsGraphSeries<>();
        ohpPointSeries = new PointsGraphSeries<>();

        for(int i=0; i<listOfWeeks.size(); i++){
            Week singleWeek = listOfWeeks.get(i);
            int weekNumber = singleWeek.getWeekNumber();
            benchPointSeries.appendData(new DataPoint(weekNumber,singleWeek.getBenchPress()),true,sizeOfList * numberOfExercises);
            squatPointSeries.appendData(new DataPoint(weekNumber,singleWeek.getSquat()),true,sizeOfList * numberOfExercises);
            deadliftPointSeries.appendData(new DataPoint(weekNumber,singleWeek.getDeadlift()),true,sizeOfList * numberOfExercises);
            ohpPointSeries.appendData(new DataPoint(weekNumber,singleWeek.getOhp()),true,sizeOfList * numberOfExercises);
        }
        if(!pointSeriesExists(graphView)){
            graphView.addSeries(benchPointSeries);
            graphView.addSeries(squatPointSeries);
            graphView.addSeries(deadliftPointSeries);
            graphView.addSeries(ohpPointSeries);
            initPointSeriesProperties();
        }
    }

    private static boolean pointSeriesExists(GraphView graphView) {
        List<Series> seriesList = graphView.getSeries();
        if(seriesList != null || !seriesList.isEmpty()){
            for (Series series:seriesList) {
                if(series instanceof PointsGraphSeries){
                    return true;
                }
            }
        }
        return false;
    }

    private static void initPointSeriesProperties() {
        benchPointSeries.setSize(2);
        squatPointSeries.setSize(2);
        deadliftPointSeries.setSize(2);
        ohpPointSeries.setSize(2);
    }
}

