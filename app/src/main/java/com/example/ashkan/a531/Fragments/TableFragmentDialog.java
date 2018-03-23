package com.example.ashkan.a531.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ashkan.a531.R;

/**
 * Created by Ashkan on 2/18/2018.
 */

public class TableFragmentDialog extends android.support.v4.app.DialogFragment {

    private static String POSSIBLE_WEEK_TO_ADD_KEY;
    private EditText mEditText;
    private Button mOkButon;
    private Button mCancelButon;
    private TableFragmentDialogListener mFragmentDialogListener;
    private int mWeekNumber;

    public static TableFragmentDialog newInstance(int possibleWeekToAdd) {
        TableFragmentDialog tableFragmentDialog = new TableFragmentDialog();
        Bundle arguments = new Bundle();
        POSSIBLE_WEEK_TO_ADD_KEY = "possibleWeekToAdd";
        arguments.putInt(POSSIBLE_WEEK_TO_ADD_KEY,possibleWeekToAdd);
        tableFragmentDialog.setArguments(arguments);
        return tableFragmentDialog;
    }

    public interface TableFragmentDialogListener{
        void insertNewWeek(int weekNumber);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            Bundle arguments = getArguments();
            int weekNumber = arguments.getInt(POSSIBLE_WEEK_TO_ADD_KEY,1);
            mWeekNumber = weekNumber;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_table,container,false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //coming from fragment
            mFragmentDialogListener = (TableFragmentDialogListener) getTargetFragment();
        }catch (ClassCastException e){
            Log.e("TableDialogFrag","Class exception error");
        }
    }

    private void initListeners() {
        mOkButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int weekNumber = Integer.parseInt(mEditText.getText().toString());
                mFragmentDialogListener.insertNewWeek(weekNumber);
                dismiss();
            }
        });

        mCancelButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initViews(View view) {
        mEditText = (EditText) view.findViewById(R.id.dialog_fragment_week_number_edit_text);
        mEditText.setText(String.valueOf(mWeekNumber));
        mOkButon = (Button) view.findViewById(R.id.dialog_fragment_week_ok_button);
        mCancelButon = (Button) view.findViewById(R.id.dialog_fragment_week_cancel_button);
    }
}
