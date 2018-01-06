package com.example.ashkan.a531.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ashkan.a531.R;

import static java.lang.Integer.parseInt;

/**
 * Created by Ashkan on 12/19/2017.
 */

public class CalculatorFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {

    private EditText weightInputEditText;
    //1,2,3,4,5,6,7,8,9,10.12.15
    //So if i did 3 reps of 150 that translates 150ib to being my 93% of my 1rpm
    //.93*x=150
    private double[] estimatedPercentage=new double[]{1.00,.95,.93,.90,.87,.85,.83,.80,.77,.75,.67,.65};
    private Spinner repsSpinner;
    private int repsPerformed;
    private Button calculatorButton;
    private int positionSelected;
    private TextView oneRepMax;
    private TextView mNewOneRepMax;
    private TextView mNinetyFiveTextView;
    private TextView mNinetyTextView;
    private TextView mEightyFiveTextView;
    private TextView mEightTextView;
    private TextView mSeventyFiveTextView;
    private TextView mSeventyTextView;
    private TextView mSixtyFiveTextView;
    private TextView mSixtyTextView;
    private TextView
            mFiftyFiveTextView;
    private TextView mFiftyTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator,container,false);
        //TODO:must use getText first for editText
        init(view);
        initPercentages(view);

        return view;
    }

    private void initPercentages(View view) {
        mNewOneRepMax = (TextView) view.findViewById(R.id.one_Rep_Max_Text_View);
        mNinetyFiveTextView = (TextView) view.findViewById(R.id.ninty_five_text_view);
        mNinetyTextView = (TextView) view.findViewById(R.id.ninty_text_view);
        mEightyFiveTextView = (TextView) view.findViewById(R.id.eighty_five_text_view);
        mEightTextView = (TextView) view.findViewById(R.id.eighty_text_view);
        mSeventyFiveTextView = (TextView) view.findViewById(R.id.seventy_five_text_view);
        mSeventyTextView = (TextView) view.findViewById(R.id.seventy_text_view);
        mSixtyFiveTextView = (TextView) view.findViewById(R.id.sixty_five_text_view);
        mSixtyTextView = (TextView) view.findViewById(R.id.sixty_text_view);
        mFiftyFiveTextView = (TextView) view.findViewById(R.id.fivety_five_text_view);
        mFiftyTextView = (TextView) view.findViewById(R.id.fivety_text_view);

    }

    private void init(View view) {
        weightInputEditText = (EditText) view.findViewById(R.id.weight_input_edit_view);

        repsSpinner= (Spinner) view.findViewById(R.id.reps_spinner);
        repsSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //TODO:getContext restricts us to min of API 23
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.reps_performed_string_array, android.R.layout.simple_spinner_item);
        //TODO:integer array for the entry gave me an error
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        repsSpinner.setAdapter(adapter);
        calculatorButton=(Button) view.findViewById(R.id.calculate_button);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weightInputEditText.getText().toString()!=""){
                    int weightEntered=Integer.parseInt(weightInputEditText.getText().toString());
                    int numberOfReps = Integer.parseInt(repsSpinner.getSelectedItem().toString());
                    double oneRepMax=findOneRepMax(weightEntered,numberOfReps);
                    linkTextViewWithNumber(oneRepMax);
                }


            }
        });

    }

    private double findOneRepMax(double weightEntered, int numberOfReps) {
        //1,2,3,4,5,6,7,8,9,10,12,15
        //So if i did 3 reps of 150 that translates 150ib to being my 93% of my 1rpm
        //.93*x=150
        //    private int[] estimatedPercentage=new int[]{100,95,93,90,87,85,83,80,77,75,67,65};
        double oneRepMax = 0;
        if(numberOfReps == 12){
            oneRepMax=weightEntered/.67;
        }
        else if(numberOfReps == 15){
            oneRepMax=weightEntered/.65;
        }
        else{
            for(int i=0;i<10;i++){
                if(numberOfReps==i+1){
                    oneRepMax=weightEntered/estimatedPercentage[i];
                    break;
                }
            }
        }
        return oneRepMax;
    }

    private void linkTextViewWithNumber(double oneRepMax) {
        mFiftyTextView.setText(calculateOneRepMax(oneRepMax,50));
        mFiftyFiveTextView.setText(calculateOneRepMax(oneRepMax,55));
        mSixtyTextView.setText(calculateOneRepMax(oneRepMax,60));
        mSixtyFiveTextView.setText(calculateOneRepMax(oneRepMax,65));
        mSeventyTextView.setText(calculateOneRepMax(oneRepMax,70));
        mSeventyFiveTextView.setText(calculateOneRepMax(oneRepMax,75));
        mEightTextView.setText(calculateOneRepMax(oneRepMax,80));
        mEightyFiveTextView.setText(calculateOneRepMax(oneRepMax,85));
        mNinetyTextView.setText(calculateOneRepMax(oneRepMax,90));
        mNinetyFiveTextView.setText(calculateOneRepMax(oneRepMax,95));
        mNewOneRepMax.setText(calculateOneRepMax(oneRepMax,100));
    }

    private String calculateOneRepMax(double oneRepMax, double percentage) {
        double result=oneRepMax*(percentage/100);
        return String.valueOf(5*(Math.round(result/5)));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        repsPerformed= parseInt(parent.getItemAtPosition(position).toString());
        positionSelected=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
