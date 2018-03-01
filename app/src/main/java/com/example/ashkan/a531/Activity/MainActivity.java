package com.example.ashkan.a531.Activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import com.example.ashkan.a531.Adapters.CustomViewPagerAdapter;
import com.example.ashkan.a531.Adapters.SetFragmentPageAdapter;
import com.example.ashkan.a531.Fragments.CalculatorFragment;
import com.example.ashkan.a531.Fragments.SetsFragment;
import com.example.ashkan.a531.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomViewPagerAdapter.OnTextChangedListener,
        SetFragmentPageAdapter.OnTextChangedListener{

    public static final String CALC_FRAG = "calcFrag";
    public static final String SETS_FRAG = "sets_frag";
    private Button calculatorFragmentButton;
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction transaction;
    private CalculatorFragment calculatorFragment;
    private SetsFragment setsFragment;
    private TabLayout tabLayout;
    private ViewPager fragmentViewPager;
    private SetFragmentPageAdapter fragmentPageAdapter;
    private ViewPager viewPager;
    private String LIST_OF_ONE_REP_MAX="listOfOneRepMax";
    private CustomViewPagerAdapter pagerAdapter;
    private ArrayList<Integer> listOf1rpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        fragmentManager= getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        setsFragment=new SetsFragment();
        ViewPager fragmentViewPager = (ViewPager) findViewById(R.id.view_pager);
        SetFragmentPageAdapter fragmentPageAdapter = new SetFragmentPageAdapter(fragmentManager);
        fragmentViewPager.setAdapter(fragmentPageAdapter);
        */
        //fragmentViewPager = (ViewPager) findViewById(R.id.fragment_view_pager);
        //viewPager =(ViewPager) findViewById(R.id.view_pager);
        setUpPagerAdapter(savedInstanceState);
        setUpFragmentAdapter(savedInstanceState);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                fragmentViewPager.setCurrentItem(position);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentViewPager.setAdapter(fragmentPageAdapter);
        //tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(fragmentViewPager);
        //setUpCustomTabs();
    }

    private void setUpPagerAdapter(Bundle savedInstanceState) {
        listOf1rpm = new ArrayList<>();
        if(savedInstanceState==null)
        {
            listOf1rpm.add(100);
            listOf1rpm.add(100);
            listOf1rpm.add(100);
            listOf1rpm.add(100);
        }
        else{
            listOf1rpm =savedInstanceState.getIntegerArrayList(LIST_OF_ONE_REP_MAX);
        }
        pagerAdapter = new CustomViewPagerAdapter(this, listOf1rpm);
        viewPager.setAdapter(pagerAdapter);
    }

    private void setUpFragmentAdapter(Bundle savedInstanceState) {
        listOf1rpm = new ArrayList<>();
        if(savedInstanceState==null)
        {
            listOf1rpm.add(100);
            listOf1rpm.add(100);
            listOf1rpm.add(100);
            listOf1rpm.add(100);
        }
        else{
            listOf1rpm=savedInstanceState.getIntegerArrayList(LIST_OF_ONE_REP_MAX);
        }

        //fragmentPageAdapter = new SetFragmentPageAdapter(getSupportFragmentManager());
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    }

    private void setUpCustomTabs() {
        ConstraintLayout benchPressLayout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_item,null);
        TextView firstExercise = (TextView) benchPressLayout.findViewById(R.id.type_of_exercise_text_view);
        firstExercise.setText("Bench Press");
        tabLayout.getTabAt(0).setCustomView(benchPressLayout);

        ConstraintLayout squatLayout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_item,null);
        TextView secondExercise = (TextView) squatLayout.findViewById(R.id.type_of_exercise_text_view);
        secondExercise.setText("Squat");
        tabLayout.getTabAt(1).setCustomView(squatLayout);

        ConstraintLayout deadLiftLayout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_item,null);
        TextView thirdExercise = (TextView) deadLiftLayout.findViewById(R.id.type_of_exercise_text_view);
        thirdExercise.setText("Deadlift");
        tabLayout.getTabAt(2).setCustomView(deadLiftLayout);

        ConstraintLayout overheadPress = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_item,null);
        TextView fourthExercise = (TextView) overheadPress.findViewById(R.id.type_of_exercise_text_view);
        fourthExercise.setText("Overhead Press");
        tabLayout.getTabAt(3).setCustomView(overheadPress);
    }


    @Override
    public int[] onWeightEntered(int positionOfPager, ArrayList<Integer> oneRepMaxList) {
        Log.v("MainActivity",""+oneRepMaxList.get(positionOfPager));
        pagerAdapter.replaceView(positionOfPager,oneRepMaxList);
        fragmentPageAdapter.replaceFragment(positionOfPager,oneRepMaxList);
        return new int[]{};
    }
}
