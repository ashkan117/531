package com.example.ashkan.a531.Data;

/**
 * Created by Ashkan on 1/9/2018.
 */

public class Exercise {
    private String exercise;
    private int oneRepMax;
    private int[][] NUMBER_OF_REPS = new int[][]{
            {5,3,1,3,3,3,5,5,5},
            {5,3,1,3,5,3,5,3,5},
            {5,3,1,3,3,3,3,3,3},
            {5,3,1,3,3,3,5,5,5},
    };
    private int[] currentNumberOfSets;

    public Exercise(String exercise, int oneRepMax){
        this.exercise=exercise;
        this.oneRepMax=oneRepMax;
        setUpArray(exercise);
    }

    private void setUpArray(String exercise) {
        switch (exercise){
            case "Bench Press":
                currentNumberOfSets=NUMBER_OF_REPS[0];
                break;
            case "Squat":
                currentNumberOfSets=NUMBER_OF_REPS[1];
                break;
            case "Deadlift":
                currentNumberOfSets=NUMBER_OF_REPS[2];
                break;
            case "Overhead Press":
                currentNumberOfSets=NUMBER_OF_REPS[3];
                break;
        }
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
        setUpArray(exercise);
    }

    public int getOneRepMax() {
        return oneRepMax;
    }

    public void setOneRepMax(int oneRepMax) {
        this.oneRepMax = oneRepMax;
    }
}
