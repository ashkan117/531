package com.example.ashkan.a531.DialogFragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashkan.a531.Interface.IHalfWeightHelperDialogFragment;
import com.example.ashkan.a531.R;
import com.example.ashkan.a531.databinding.DialogFragmentWeightHelperBinding;

/**
 * Created by Ashkan on 3/27/2018.
 */

public class HalfWeightHelperDialogFragment extends DialogFragment implements IHalfWeightHelperDialogFragment {

    EditText mHalfWeightEditText;
    Button mDoneButton;
    TextView mResultTextView;

    DialogFragmentWeightHelperBinding mBinding;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.dialog_fragment_weight_helper,container,false);
        mContext = getContext();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_weight_helper,container,false);
        mBinding.setIHalfWeightHelperDialogFragment(this);
        return mBinding.getRoot();
    }

    @Override
    public void onButtonClicked() {
        double halfWeight = Double.parseDouble(mBinding.halfWeightHelperEditText.getText().toString());
        double result = (halfWeight * 2) + 45;
        mBinding.setHalfWeightInput(result);
    }
}
