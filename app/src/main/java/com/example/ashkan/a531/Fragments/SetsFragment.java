package com.example.ashkan.a531.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashkan.a531.Adapters.SetFragmentPageAdapter;
import com.example.ashkan.a531.Interface.IMainScreen;
import com.example.ashkan.a531.Model.Week;
import com.example.ashkan.a531.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashkan on 12/19/2017.
 */

public class SetsFragment extends android.support.v4.app.Fragment implements SetFragmentPageAdapter.UpdateTabLayoutListener
        ,IMainScreen{

    public static final String ONE_REP_MAX_LIST = "ONE_REP_MAX_LIST";

    private static String POSITION_OF_PAGER_KEY = "POSITION_OF_PAGER_KEY";
    private static String WEIGHT_LIFTED= "weightLifted";;
    private static ArrayList<Integer> mOneRepMaxList= new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context mContext;
    private int mPositionOfPager;
    private String mTypeOfExercise;
    private int mWeightLifted;
    private TabLayout mTabLayout;
    private ViewPager mFragmentViewPager;
    private SetFragmentPageAdapter mFragmentPagerAdapter;
    private FragmentActivity mActivity;
    private ArrayList<ConstraintLayout> mListOfTabItems;
    private ContentResolver mContectResolver;
    private SetsFragmentEndingListener mActivityListener;

    @Override
    public Week getCurrentWeekFromSetFragment() {
        Week week = new Week();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.SETS_FRAGMENT_SHARED_PREFERENCE),Context.MODE_PRIVATE);
        int benchPress = sharedPreferences.getInt(getString(R.string.BENCH_PRESS_PREFERENCE),-1);
        int squat = sharedPreferences.getInt(getString(R.string.SQUAT_PREFERENCE),-1);
        int deadlift = sharedPreferences.getInt(getString(R.string.DEADLIFT_PREFERENCE),-1);
        int ohp = sharedPreferences.getInt(getString(R.string.OHP_PREFERENCE),-1);
        week.setBenchPress(benchPress);
        week.setSquat(squat);
        week.setDeadlift(deadlift);
        week.setOhp(ohp);
        return week;
    }


    public interface SendingMainScreenWeekInformation{
        public void sendWeekInformationToMainScreen(Week week);
    }
    public interface SetsFragmentEndingListener{
        void removeFragmentFromMainScreen();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        setHasOptionsMenu(true);
        loadSharedPreferences(savedInstanceState);
        loadBundle(savedInstanceState);
        mListOfTabItems = new ArrayList<>();
        mContext = getContext();
        mActivity = getActivity();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mActivityListener = (SetsFragmentEndingListener) context;
        }catch (ClassCastException e){
            Log.v("SetsFragment", "Activity did not implement SetsFragmentEndingListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //saves long term data
//        SharedPreferences preferences = getActivity().getSharedPreferences("SetsFragmentPreferences",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        for(int i=0;i<mOneRepMaxList.size();i++){
//            String key = ONE_REP_MAX_LIST+i;
//            editor.putInt(key,mOneRepMaxList.get(i));
//        }
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.SETS_FRAGMENT_SHARED_PREFERENCE), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.BENCH_PRESS_PREFERENCE),mOneRepMaxList.get(0));
        editor.putInt(getString(R.string.SQUAT_PREFERENCE),mOneRepMaxList.get(1));
        editor.putInt(getString(R.string.DEADLIFT_PREFERENCE),mOneRepMaxList.get(2));
        editor.putInt(getString(R.string.OHP_PREFERENCE),mOneRepMaxList.get(3));
        editor.commit();
        //mActivityListener.removeFragmentFromMainScreen();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("SetsFragment","onActivityCreated called");
        SharedPreferences preferences = getActivity().getSharedPreferences("SetsFragmentPreferences",Context.MODE_PRIVATE);
        String key = ONE_REP_MAX_LIST+0;
        int checkInt = preferences.getInt(key,-1);
        if(checkInt!=-1){
            for(int i=0;i<mOneRepMaxList.size();i++){
                int oneRepMax = preferences.getInt(ONE_REP_MAX_LIST+i,-1);
                mOneRepMaxList.set(i,oneRepMax);
            }
        }
        else if(savedInstanceState!=null)
        {
            mPositionOfPager = savedInstanceState.getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = savedInstanceState.getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=savedInstanceState.getIntegerArrayList(ONE_REP_MAX_LIST);

        }
        else {
            //would've added 4 to the list each time the sets fragment is opened?
            if(mOneRepMaxList.size()==0){
                for (int i=0;i<4;i++){
                    mOneRepMaxList.add(100);
                }
            }

            mWeightLifted=100;
            mPositionOfPager=0;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("SetsFragment","onDestroyView called");
    }



    private void loadSharedPreferences(Bundle savedInstanceState) {
        //TODO:Had an issue with trying to save a potentially larger list with shared preference
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.SETS_FRAGMENT_SHARED_PREFERENCE),0);
        if(preferences.getInt(getString(R.string.BENCH_PRESS_PREFERENCE),-1)!=-1){
            mOneRepMaxList.add(preferences.getInt(getString(R.string.BENCH_PRESS_PREFERENCE),-1));
            mOneRepMaxList.add(preferences.getInt(getString(R.string.SQUAT_PREFERENCE),-1));
            mOneRepMaxList.add(preferences.getInt(getString(R.string.DEADLIFT_PREFERENCE),-1));
            mOneRepMaxList.add(preferences.getInt(getString(R.string.OHP_PREFERENCE),-1));
        }
//        if(preferences.getInt(BENCH_PRESS_PREFERENCE,-1)!=-1){
//        for(int i=0;i<mOneRepMaxList.size();i++) {
//            String key = ONE_REP_MAX_LIST + i;
//            preferences.getInt(key, mOneRepMaxList.get(i));
//        }
//        }
        else if(savedInstanceState!=null){
            mOneRepMaxList=savedInstanceState.getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        else{
            for(int i=0;i<4;i++)
            {
                mOneRepMaxList.add(100);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getArguments()!=null)
        {
            mPositionOfPager = getArguments().getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = getArguments().getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=getArguments().getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        //saves current data
        outState.putIntegerArrayList(ONE_REP_MAX_LIST, mOneRepMaxList);
        outState.putInt(POSITION_OF_PAGER_KEY, mPositionOfPager);
        outState.putInt(WEIGHT_LIFTED, mOneRepMaxList.get(mPositionOfPager));


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_set,container,false);
        initViews(view);
        loadSharedPreferences(savedInstanceState);
        setUpCustomTabView();
        return view;
    }



    private void loadBundle(Bundle savedInstanceState) {
        if(savedInstanceState!=null)
        {
            mPositionOfPager = savedInstanceState.getInt(POSITION_OF_PAGER_KEY,0);
            mWeightLifted = savedInstanceState.getInt(WEIGHT_LIFTED,99);
            mOneRepMaxList=savedInstanceState.getIntegerArrayList(ONE_REP_MAX_LIST);
        }
        else {
            for (int i=0;i<4;i++){
                mOneRepMaxList.add(100);
            }
            mWeightLifted=100;
            mPositionOfPager=0;
        }
    }


    private void initViews(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.set_tab_layout);
        mFragmentViewPager = (ViewPager) rootView.findViewById(R.id.set_fragment_view_pager);
        mFragmentPagerAdapter = new SetFragmentPageAdapter(mContext,this ,getFragmentManager());
        //adds the items to the pager adapter
        //these items are responsible for retrieving recyclerview data

        //possible issue with adding fragment
        // on second run we get extras?
        mFragmentPagerAdapter.addFragment(0,SetFragmentItem.newInstance(0, mOneRepMaxList));
        mFragmentPagerAdapter.addFragment(1,SetFragmentItem.newInstance(1, mOneRepMaxList));
        mFragmentPagerAdapter.addFragment(2,SetFragmentItem.newInstance(2, mOneRepMaxList));
        mFragmentPagerAdapter.addFragment(3,SetFragmentItem.newInstance(3, mOneRepMaxList));
        mFragmentViewPager.setAdapter(mFragmentPagerAdapter);
        manageSwipesAndClicks();
        //mTabLayout.setupWithViewPager(mFragmentViewPager,true);
        mTabLayout.bringToFront();
    }

    private void manageSwipesAndClicks() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentViewPager.setCurrentItem(mTabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mFragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabLayout.setScrollPosition(position,positionOffset,true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.weight_help_setting:
                WeightHelperDialogFragment dialogFragment = new WeightHelperDialogFragment();
                //Nested Fragments must use ChildFragmentMangaer
                //FragmentManager manager = ((AppCompatActivity)mContext).getFragmentManager();
                android.support.v4.app.FragmentManager manager = getChildFragmentManager();
                dialogFragment.show(manager,"weightHelperDialogFragment");
                return true;
                /*
            case R.id.sets_action_notifications:
                Intent intent = new Intent(getContext(), AlarmClockActivity.class);
                startActivity(intent);
                return true;
            case R.id.sets_action_settings:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                return true;
                */
            case R.id.timer_setting:
                //TODO: Caution: If you invoke an intent and there is no app available on the device that can handle the intent, your app will crash.
                //TODO: Permission should be outside application in manifest?? Looks like it
                Intent clockIntent = new Intent(AlarmClock.ACTION_SET_TIMER);
                clockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //Makes sure that the phone can handle this intent
                //If not app would crash and we'd need to handle it
                PackageManager packageManager = getContext().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(clockIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) {
                    startActivity(clockIntent);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO: the second parameter should NOT be null. It means the menu to inflate to

        inflater.inflate(R.menu.sets_fragment_menu,menu);
    }

    private void setUpCustomTabView() {
        //TODO: tablayout should be inside viewpager in XML (May have made things better)
        //Sets up our tabs
        setUpCustomTabs(0,"Bench Press");
        setUpCustomTabs(1,"Squat");
        setUpCustomTabs(2,"Deadlift");
        setUpCustomTabs(3,"Overhead Press");
    }

    @Override
    public void onResume() {
        super.onResume();
        selectPage(mTabLayout.getSelectedTabPosition());
    }
    /*
       * Controls that when a tab is selected we want to move there and get rid of the focus that the EditText does
       * First we request the focus from that edittext which shifts its attention the position we are ate
       * THen we clear the focus so that the flashing cursor goes away
       * */
    void selectPage(int pageIndex){
        mFragmentViewPager.setCurrentItem(pageIndex);
        // get the position of the currently selected tab and set selected to false
        mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getCustomView().setSelected(false);
        // set selected to true on the desired tab
        mTabLayout.getTabAt(pageIndex).getCustomView().setSelected(true);

        mTabLayout.setScrollPosition(pageIndex,0f,true);
        mListOfTabItems.get(pageIndex).findViewById(R.id.weight_edit_text_view).requestFocus();
        mListOfTabItems.get(pageIndex).findViewById(R.id.weight_edit_text_view).clearFocus();
    }

    private void setUpCustomTabs(final int position, String exerciseType) {
        final ConstraintLayout tabGroupView = (ConstraintLayout) mActivity.getLayoutInflater().inflate(R.layout.custom_tab_item,null);
        final EditText oneRepMaxEditText = (EditText) tabGroupView.findViewById(R.id.weight_edit_text_view);
        TextView title = (TextView) tabGroupView.findViewById(R.id.type_of_exercise_text_view);
        title.setText(exerciseType);
        oneRepMaxEditText.setText(mOneRepMaxList.get(position).toString());
        String input=oneRepMaxEditText.getText().toString();
        mListOfTabItems.add(tabGroupView);
        oneRepMaxEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    Log.v("CustomViewPageAdapter","newWEight entered is "+text);
                    mOneRepMaxList.set(position,Integer.parseInt(text));
                    //SetsFragment setsFragment= SetsFragment.newInstance(position,mOneRepMaxList);

                    mFragmentPagerAdapter.replaceFragment(position, mOneRepMaxList);
                    //selectPage(mTabLayout.getSelectedTabPosition());
                    selectPage(position);
                    closeKeyBoard();
                    mFragmentPagerAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });


        mTabLayout.addTab(mTabLayout.newTab(),position);
        mTabLayout.getTabAt(position).setCustomView(tabGroupView);
        selectPage(position);
        closeKeyBoard();

    }

    public void closeKeyBoard()
    {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = mActivity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(mActivity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void updateTabLayoutPosition(int position, ArrayList<Integer> oneRepMaxList) {
        selectPage(position);
        closeKeyBoard();
    }




}
