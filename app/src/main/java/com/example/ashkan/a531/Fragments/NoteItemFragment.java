package com.example.ashkan.a531.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ashkan.a531.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteItemFragment extends android.support.v4.app.Fragment {

    private static String NOTE_STRING="noteString";
    private String note;
    private EditText mNoteEditText;
    private String mSavedNote;

    public NoteItemFragment() {
        // Required empty public constructor
    }

    public static NoteItemFragment newInstance(String note) {

        Bundle args = new Bundle();
        NoteItemFragment fragment = new NoteItemFragment();
        args.putString(NOTE_STRING,note);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mSavedNote = getArguments().getString(NOTE_STRING);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments()!=null){
            mSavedNote = getArguments().getString(NOTE_STRING);
            mNoteEditText.setText(mSavedNote);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_note_item, container, false);
        mNoteEditText = (EditText) rootView.findViewById(R.id.note_item_edit_text);
        if(getArguments()!=null){
            mSavedNote = getArguments().getString(NOTE_STRING);
        }
        mNoteEditText.setText(mSavedNote);
        return rootView;
    }


    public String getNote() {
        if(mNoteEditText!=null){
            return mNoteEditText.getText().toString();
        }
        return null;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
