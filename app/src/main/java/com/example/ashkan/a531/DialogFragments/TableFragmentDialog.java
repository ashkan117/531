package com.example.ashkan.a531.DialogFragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashkan.a531.R;
import com.example.ashkan.a531.databinding.DialogFragmentTableBinding;

/**
 * Created by Ashkan on 2/18/2018.
 */

public class TableFragmentDialog extends android.support.v4.app.DialogFragment {

    private static String POSSIBLE_WEEK_TO_ADD_KEY;
    private TableFragmentDialogListener mFragmentDialogListener;
    private int mWeekNumber;
    private DialogFragmentTableBinding mDialogFragmentTableBinding;

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
        //View view = inflater.inflate(R.layout.dialog_fragment_table,container,false);
        mDialogFragmentTableBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_fragment_table,container,false);
        initListeners();
        return mDialogFragmentTableBinding.getRoot();
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
        mDialogFragmentTableBinding.dialogFragmentWeekOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weekNumberString = mDialogFragmentTableBinding.dialogFragmentWeekNumberEditText.getText().toString();
                int weekNumber = Integer.parseInt(weekNumberString.trim());
                mFragmentDialogListener.insertNewWeek(weekNumber);
                dismiss();
            }
        });

        mDialogFragmentTableBinding.dialogFragmentWeekCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
