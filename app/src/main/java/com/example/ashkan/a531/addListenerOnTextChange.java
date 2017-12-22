package com.example.ashkan.a531;

/**
 * Created by Ashkan on 12/21/2017.
 */


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
//You should create a Listener class like so,
// Just modify the parameters in the constructor to accept the EditText ID you want to add a listener to.

public class addListenerOnTextChange implements TextWatcher {
    private Context mContext;
    EditText mEdittextview;

    public addListenerOnTextChange(Context context, EditText edittextview) {
        super();
        this.mContext = context;
        this.mEdittextview= edittextview;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //What you want to do
    }
}
