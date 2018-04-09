package com.example.ashkan.a531.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.ashkan.a531.Interface.ICalculatorFragment;
import com.example.ashkan.a531.R;
import com.example.ashkan.a531.databinding.FragmentCalculatorBinding;

import static java.lang.Integer.parseInt;

/**
 * Created by Ashkan on 12/19/2017.
 */

public class CalculatorFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener,
        ICalculatorFragment{

    private EditText weightInputEditText;
    //1,2,3,4,5,6,7,8,9,10.12.15
    //So if i did 3 reps of 150 that translates 150ib to being my 93% of my 1rpm
    //.93*x=150
    private double[] estimatedPercentage=new double[]{1.00,.95,.93,.90,.87,.85,.83,.80,.77,.75,.67,.65};
    private int repsPerformed;
    private FragmentCalculatorBinding mFragmentCalculatorBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mFragmentCalculatorBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_calculator,container,false);
        mFragmentCalculatorBinding.setICalculatorFragment((ICalculatorFragment) this);
        mFragmentCalculatorBinding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorFragment.this.onClick(v);
            }
        });
        setHasOptionsMenu(true);
        //TODO:must use getText first for editText
        init(mFragmentCalculatorBinding.getRoot());
        return mFragmentCalculatorBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_calculator,menu);
    }

    private void init(View view) {
        mFragmentCalculatorBinding.repsSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //TODO:getContext restricts us to min of API 23

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.reps_performed_string_array, android.R.layout.simple_spinner_item);
        //TODO:integer array for the entry gave me an error

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mFragmentCalculatorBinding.repsSpinner.setAdapter(adapter);
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

    public String calculateOneRepMax(double oneRepMax, double percentage) {
        double result=oneRepMax*(percentage/100);
        return String.valueOf(5*(Math.round(result/5)));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        repsPerformed= parseInt(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        String weightEnteredString = mFragmentCalculatorBinding.weightInputEditView.getText().toString();
        int weightEntered = Integer.parseInt(weightEnteredString);
        int numberOfReps = Integer.parseInt(mFragmentCalculatorBinding.repsSpinner.getSelectedItem().toString());;
        double oneRepMax = findOneRepMax(weightEntered,numberOfReps);
        mFragmentCalculatorBinding.setOneRepMax(oneRepMax);
    }
}
