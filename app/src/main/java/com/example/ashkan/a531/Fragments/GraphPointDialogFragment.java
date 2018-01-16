package com.example.ashkan.a531.Fragments;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ashkan.a531.Data.DataManager;
import com.example.ashkan.a531.Data.Week;
import com.example.ashkan.a531.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphPointDialogFragment extends android.support.v4.app.DialogFragment {


    private EditText weekNumberEditText;
    private EditText oneRepMaxEditText;
    private Spinner exerciseSpinner;
    private Button okButton;
    private Button cancelButton;
    private int mWeekNumber;
    private int mOneRepMax;
    private String mExercise;
    private DialogListener mDialogListener;
    private InputMethodManager imm;

    public interface DialogListener{
        void returnInformationFromDialogToActivity(int weekNumber, String exerciseType, int oneRepMax);
    }


    public GraphPointDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewGroup = inflater.inflate(R.layout.fragment_graph_point_dialog, container, false);
        weekNumberEditText = (EditText) viewGroup.findViewById(R.id.week_number_edit_text);
        oneRepMaxEditText = (EditText) viewGroup.findViewById(R.id.one_rep_max_week_edit_text);
        exerciseSpinner = (Spinner) viewGroup.findViewById(R.id.exercise_names_spinner);
        okButton = (Button) viewGroup.findViewById(R.id.ok_button);
        cancelButton = (Button) viewGroup.findViewById(R.id.cancel_button);


        setUpListeners();

        return viewGroup;
    }

    private void setUpListeners() {
//        weekNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE)
//                {
//                    mWeekNumber=Integer.parseInt(v.getText().toString());
//                    closeKeyBoard(weekNumberEditText);
//                    return true;
//                }
//                return false;
//            }
//        });

//        oneRepMaxEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE)
//                {
//                    mOneRepMax=Integer.parseInt(v.getText().toString());
//                    closeKeyBoard(oneRepMaxEditText);
//                    return true;
//                }
//                return false;
//            }
//        });

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mExercise=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mExercise=parent.getSelectedItem().toString();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWeekNumber=Integer.parseInt(weekNumberEditText.getText().toString());
                mOneRepMax=Integer.parseInt(oneRepMaxEditText.getText().toString());
                mExercise = exerciseSpinner.getSelectedItem().toString();
                if(mExercise==null||mOneRepMax==0||mWeekNumber==0){
                    Toast.makeText(getContext(),"One of the fields have not been entered",Toast.LENGTH_SHORT).show();
                }
                else{
                    getDialog().cancel();
                    mDialogListener.returnInformationFromDialogToActivity(mWeekNumber,mExercise,mOneRepMax);
                }
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    public void closeKeyBoard(View view)
    {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mDialogListener = (DialogListener) getTargetFragment();
        }catch (ClassCastException e)
        {
            Log.e("GraphPointDialogFrag","Class exception error");
        }
    }
}
