package com.example.ashkan.a531.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashkan.a531.R;

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
    private Button weightEnteredButton;

    public void replaceView(int positionOfPager, ArrayList<Integer> oneRepMaxList) {
        mOneRepMaxList.set(positionOfPager,oneRepMaxList.get(positionOfPager));
    }

    public interface OnTextChangedListener{
        int[] onWeightEntered (int positionOfPager, ArrayList<Integer> weightEntered);
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
    public Object instantiateItem(final ViewGroup container, final int position) {
        //ready the viewItem
            //therefore we must inflate ht eparent to access
        View viewGroup=this.mLayoutInflater.inflate(R.layout.custom_tab_item,container,false);
        typeOfExerciseTextView = (TextView) viewGroup.findViewById(R.id.type_of_exercise_text_view);
        typeOfExerciseTextView.setText(mListOfExerciseNames.get(position));
        oneRepMaxEditText = (EditText) viewGroup.findViewById(R.id.weight_edit_text_view);
        final int tempPosition=position;
        Log.v("CustomViewPageAdapter","newWEight entered is "+oneRepMaxEditText.getText().toString());
        //Got rid of TextWatcher, wasnt working properly, need to relearn and retry later
        if(mOneRepMaxList.get(position)!=null)
        {
            //TODO: toString fixes it but why? Isnt the list already a string
            //NO ITS NOT. ITS AN INT THAT REPRESENTS 1RPM
            oneRepMaxEditText.setText(mOneRepMaxList.get(position).toString());
        }
        Log.v("CustomViewPageAdapter","newWEight entered is "+oneRepMaxEditText.getText().toString());
        oneRepMaxEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    Log.v("CustomViewPageAdapter","newWEight entered is "+v.getText().toString());
                    mOneRepMaxList.set(position,Integer.parseInt(text));
                    listener.onWeightEntered(tempPosition,mOneRepMaxList);
                    return true;
                }
                return false;
            }
        });
        container.addView(viewGroup);

        //TODO: MUST ADD VIEW to Container(In this case this is the ViewPager)
        return viewGroup;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) mListOfExerciseNames.toArray()[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
