package com.example.ashkan.a531.DialogFragments;


import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ashkan.a531.R;
import com.example.ashkan.a531.databinding.FragmentWeightHelperBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightHelperDialogFragment extends android.support.v4.app.DialogFragment {

    private FragmentWeightHelperBinding mWeightHelperBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Decided to use DialogFragment instead of AlertDialog since as soon as a button is pressed
        //  with alert the window automatically closes
        mWeightHelperBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weight_helper,container,false);
        mWeightHelperBinding.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = mWeightHelperBinding.helperInputEditText.getText().toString();
                if (!inputString.isEmpty() && inputString != "") {
                    final int weight = Integer.parseInt(inputString);
                    final double halfWeight = (weight - 45) / 2.0;
                    mWeightHelperBinding.setHalfWeight(halfWeight);
                    Toast.makeText(getContext(), "" + halfWeight, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "The weight entered is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mWeightHelperBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return mWeightHelperBinding.getRoot();
    }
}


