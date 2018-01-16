package com.example.ashkan.a531.Fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashkan.a531.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightHelperDialogFragment extends DialogFragment {

    private String inputString;
    private Button okButton;
    private Button cancelbutton;


    private View rootView;
    private EditText userInputEditText;
    private TextView weightOnOneSideTextView;

    public WeightHelperDialogFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Decided to use DialogFragment instead of AlertDialog since as soon as a button is pressed
        //  with alert the window automatically closes

        rootView = (View) inflater.inflate(R.layout.fragment_weight_helper, null);
        userInputEditText = (EditText) rootView.findViewById(R.id.helper_edit_text);
        weightOnOneSideTextView = (TextView) rootView.findViewById(R.id.helper_text_view);
        okButton = (Button) rootView.findViewById(R.id.ok_button);
        cancelbutton = (Button) rootView.findViewById(R.id.cancel_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString = userInputEditText.getText().toString();
                if (!inputString.isEmpty() && inputString != "") {
                    final int weight = Integer.parseInt(inputString);
                    final int halfWeight = (weight - 45) / 2;
                    weightOnOneSideTextView.setText(String.valueOf(halfWeight));
                    Toast.makeText(getContext(), "" + halfWeight, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "The weight entered is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }
}


