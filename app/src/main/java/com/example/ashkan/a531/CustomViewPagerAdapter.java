package com.example.ashkan.a531;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static java.lang.Integer.parseInt;

/**
 * Created by Ashkan on 12/21/2017.
 */

public class CustomViewPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final ArrayList<Integer> mOneRepMaxList;
    private ArrayList<String> mListOfExerciseNames=new ArrayList<>();
    private int NUMBER_OF_EXERCISES=4;
    private TextView typeOfExerciseTextView;
    private EditText oneRepMaxEditText;
    private OnTextChangedListener listener;
    interface OnTextChangedListener{
        int[] onWeightEntered (int positionOfPager, int weightEntered);
    }

    public CustomViewPagerAdapter(Context context, List<Integer> oneRepMaxList){
        mContext =context;
        mLayoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mOneRepMaxList =(ArrayList<Integer>) oneRepMaxList;
        mListOfExerciseNames.add("Bench Press");
        mListOfExerciseNames.add("Squat");
        mListOfExerciseNames.add("Deadlift");
        mListOfExerciseNames.add("Overhead Press");
        //WORKS
        listener=(OnTextChangedListener) context;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_EXERCISES;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //ready the viewItem
            //therefore we must inflate ht eparent to access
        Log.v("PageAdapter","Position is "+position);
        View viewGroup=this.mLayoutInflater.inflate(R.layout.custom_tab_item,container,false);
        typeOfExerciseTextView = (TextView) viewGroup.findViewById(R.id.type_of_exercise_text_view);
        typeOfExerciseTextView.setText(mListOfExerciseNames.get(position));
        oneRepMaxEditText = (EditText) viewGroup.findViewById(R.id.weight_edit_text_view);
        final int tempPosition=position;
        oneRepMaxEditText.addTextChangedListener(new TextWatcher() {
            boolean _ignore=false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO:makes sure that we only do it once each time
                if (_ignore)
                    return;

                _ignore = true; // prevent infinite loop
                // Change your text here.
                String versionString=s.toString();
                int versionInt=Integer.parseInt(versionString);
                oneRepMaxEditText.setText(s.toString());
                mOneRepMaxList.set(tempPosition,Integer.parseInt(s.toString()));
                listener.onWeightEntered(tempPosition,versionInt);
                //TODO:implement interface to allow us to update
                //TODO:changing edittext changes adjacent ones
                _ignore = false; // release, so the TextWatcher start to listen again.
            }
        });
        if(mOneRepMaxList.get(position)!=null)
        {
            //TODO: toString fixes it but why? Isnt the list already a string
            //NO ITS NOT. ITS AN INT THAT REPRESENTS 1RPM
            oneRepMaxEditText.setText(mOneRepMaxList.get(position).toString());
        }
        container.addView(viewGroup);
        //TODO: MUST ADD VIEW to Container(In this case this is the ViewPager)
        return viewGroup;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
