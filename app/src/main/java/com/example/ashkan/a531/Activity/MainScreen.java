package com.example.ashkan.a531.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ashkan.a531.Adapters.CustomViewPagerAdapter;
import com.example.ashkan.a531.Adapters.SetFragmentPageAdapter;
import com.example.ashkan.a531.DialogFragments.HalfWeightHelperDialogFragment;
import com.example.ashkan.a531.FragmentTag;
import com.example.ashkan.a531.Fragments.CalculatorFragment;
import com.example.ashkan.a531.Fragments.GraphFragment;
import com.example.ashkan.a531.Fragments.GraphPointDialogFragment;
import com.example.ashkan.a531.Fragments.NotesFragment;
import com.example.ashkan.a531.Fragments.SetsFragment;
import com.example.ashkan.a531.Fragments.TableFragment;
import com.example.ashkan.a531.Fragments.WeightHelperDialogFragment;
import com.example.ashkan.a531.Model.Week;
import com.example.ashkan.a531.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        CustomViewPagerAdapter.OnTextChangedListener,
        GraphPointDialogFragment.GetWeekInformationFromSetFragment ,
        SetsFragment.SetsFragmentEndingListener,
        BottomNavigationViewEx.OnNavigationItemSelectedListener{

    private static final int NAV_ITEM_WORKOUT_INDEX = 0;
    private static final int NAV_ITEM_PROGRESS_INDEX = 1;
    private static final int NAV_ITEM_TABLE_INDEX = 2;
    private static final int NAV_ITEM_NOTES_INDEX = 3;
    private static final int NAV_ITEM_CALCULATOR_INDEX = 4;

    private static final int BOTTOM_NAV_ITEM_WORKOUT_INDEX = 0;
    private static final int BOTTOM_NAV_ITEM_CALCULATOR_INDEX = 1;
    private static final int BOTTOM_NAV_ITEM_NOTES_INDEX = 2;
    private static final int BOTTOM_NAV_ITEM_PROGRESS_INDEX = 3;

    private ArrayList<Integer> mOneRepMaxList;
    private ViewPager fragmentViewPager;
    private CustomViewPagerAdapter tabViewPagerAdapter;
    private SetFragmentPageAdapter fragmentPageAdapter;
    private TabLayout tabLayout;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    public static final String ONE_REP_MAX_LIST = "ONE_REP_MAX_LIST";
    private EditText oneRepMaxEditText;
    private String PREFS_NAME="MainScreenSharedPrefs";
    private SetsFragment mSetsFragment;
    private NotesFragment mNotesFragment;
    private CalculatorFragment mCalculatorFragment;
    private GraphFragment mGraphFragment;
    private NavigationView mNavigationView;
    private TableFragment mTableFragment;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emptyFragmentIfExists();
    }

    private void emptyFragmentIfExists() {
        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.main_screen_fragment_container);
        if(currentFragment!=null){
            mFragmentManager.beginTransaction().remove(currentFragment);
        }
    }

    private ConstraintLayout tabAndViewPagerLayout;
    private int navMenuIndex = 0;
    private BottomNavigationViewEx mBottomNavigationViewEx;
    private int NOTES_MENU_INDEX =2 ;
    private int PROGRESS_NAV_MENU_ITEM=1;
    private int CALCULATOR_NAV_MENU_INDEX = 3;
    private int SETS_NAV_MENU_INDEX=0;
    private int TABLE_NAV_MENU_INDEX;
    private View otherFragmentsLayout;
    private String ACTIVE_FRAGMENT="";
    private ContentResolver mContectResolver;

    //Simulates the stack
    private ArrayList<String> mFragmentTags = new ArrayList<>();
    //How we keep track of which fragments are active
    private ArrayList<FragmentTag> mFragment = new ArrayList<>();

    @Override
    public void onBackPressed() {

        int backStackCount = mFragmentTags.size();
        if(mFragmentTags.size()>1){
            String topFragment = mFragmentTags.get(backStackCount - 1);

            String newFragment = mFragmentTags.get(backStackCount - 2);

            mFragmentTags.remove(topFragment);

            setFragmentVisibilities(newFragment);

        }
        else {
            super.onBackPressed();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putIntegerArrayList(ONE_REP_MAX_LIST,mOneRepMaxList);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO:         android:fitsSystemWindows="false"  fixes navigation being cut off
        Log.v("MainScreen","Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        invalidateOptionsMenu();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();
        emptyFragmentIfExists();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mOneRepMaxList = new ArrayList<>();

        loadSharedPreferences(savedInstanceState);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        init();



    }

    private void loadSharedPreferences(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,0);
        if(preferences.getInt(getString(R.string.BENCH_PRESS_PREFERENCE),-1)!=-1){
            mOneRepMaxList.add(preferences.getInt(getString(R.string.BENCH_PRESS_PREFERENCE),-1));
            mOneRepMaxList.add(preferences.getInt(getString(R.string.SQUAT_PREFERENCE),-1));
            mOneRepMaxList.add(preferences.getInt(getString(R.string.DEADLIFT_PREFERENCE),-1));
            mOneRepMaxList.add(preferences.getInt(getString(R.string.OHP_PREFERENCE),-1));
        }
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

    private void init(){

        mBottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        mBottomNavigationViewEx.enableShiftingMode(false);
        mBottomNavigationViewEx.enableItemShiftingMode(false);
        mBottomNavigationViewEx.enableAnimation(false);

        //Only add the fragment if its empty
        if(mSetsFragment==null){
            mSetsFragment = new SetsFragment();
            navMenuIndex = SETS_NAV_MENU_INDEX;
            mFragmentTransaction.add(R.id.main_screen_fragment_container,mSetsFragment,
                    getString(R.string.tag_fragment_sets));
            mFragmentTransaction.commit();
            mFragmentTags.add(getString(R.string.tag_fragment_sets));
            mFragment.add(new FragmentTag(mSetsFragment, getString(R.string.tag_fragment_sets)));
        }
        else{
            mFragmentTags.remove(getString(R.string.tag_fragment_sets));
            mFragmentTags.add(getString(R.string.tag_fragment_sets));
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_sets));



        otherFragmentsLayout = findViewById(R.id.main_screen_fragment_container);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(navMenuIndex== SETS_NAV_MENU_INDEX){
            getMenuInflater().inflate(R.menu.sets_fragment_menu, menu);
        }
        else if(navMenuIndex==NOTES_MENU_INDEX){
            getMenuInflater().inflate(R.menu.fragment_notes_menu,menu);
        }
        else if(navMenuIndex == CALCULATOR_NAV_MENU_INDEX){
            getMenuInflater().inflate(R.menu.menu_calculator,menu);
        }
        else {
            getMenuInflater().inflate(R.menu.main_screen, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.sets_action_notifications) {
            Intent intent = new Intent(this,AlarmClockActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.sets_action_settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        */
        if(id == R.id.weight_help_setting){
            WeightHelperDialogFragment dialogFragment = new WeightHelperDialogFragment();
            dialogFragment.show(getSupportFragmentManager(),getString(R.string.tag_weight_helper_dialog_fragment));
            return true;
        }
        else if(id == R.id.helper_action){
            HalfWeightHelperDialogFragment halfWeightHelperDialogFragment = new HalfWeightHelperDialogFragment();
            halfWeightHelperDialogFragment.show(getSupportFragmentManager(),getString(R.string.tag_half_weight_helper_dialog_fragment));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(ONE_REP_MAX_LIST,mOneRepMaxList);
    }

    @Override
    public int[] onWeightEntered(int positionOfPager, ArrayList<Integer> updatedOneRepMaxList) {
        Log.v("MainActivity",""+ mOneRepMaxList.get(positionOfPager));
        //DataManager.saveCurrentInput(weightEntered, dbHelper);
        tabViewPagerAdapter.replaceView(positionOfPager, mOneRepMaxList);
        fragmentPageAdapter.replaceFragment(positionOfPager, mOneRepMaxList);
        return new int[]{};
    }

/*
    * ReplaceFragment calls this method to allow us to update the tablayout to the newly entered numbers
    * Otherwise the page goes blank
    */

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.BENCH_PRESS_PREFERENCE),mOneRepMaxList.get(0));
        editor.putInt(getString(R.string.SQUAT_PREFERENCE),mOneRepMaxList.get(1));
        editor.putInt(getString(R.string.DEADLIFT_PREFERENCE),mOneRepMaxList.get(2));
        editor.putInt(getString(R.string.OHP_PREFERENCE),mOneRepMaxList.get(3));
        editor.commit();
        emptyFragmentIfExists();
    }


//    @Override
//    public Week getWeekInfo() {
//        int weekNumber=-1;
//        int bench = mOneRepMaxList.get(0);
//        int squat = mOneRepMaxList.get(1);
//        int deadlift = mOneRepMaxList.get(2);
//        int ohp = mOneRepMaxList.get(3);
//        return new Week(weekNumber,bench,squat,deadlift,ohp);
//    }


    @Override
    public void removeFragmentFromMainScreen() {
        Fragment setsFragment = mFragmentManager.findFragmentById(R.id.main_screen_fragment_container);
        if(setsFragment instanceof SetsFragment){
            mFragmentTransaction = mFragmentManager.beginTransaction().remove(setsFragment);
            mFragmentTransaction.commit();
        }

    }

    /***
     * This handles the case for the bottom navigation view and the navigation drawer
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.nav_item_weights: {
            }
            case R.id.bottom_nav_workout: {
                navMenuIndex = SETS_NAV_MENU_INDEX;
                mFragmentTags.remove(getString(R.string.tag_fragment_sets));
                mFragmentTags.add(getString(R.string.tag_fragment_sets));
                mNavigationView.getMenu().getItem(NAV_ITEM_WORKOUT_INDEX).setChecked(true);
                mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_WORKOUT_INDEX).setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_sets));
                break;
            }

            case R.id.nav_item_progress: {
            }
            case R.id.bottom_nav_progress: {
                if(mGraphFragment == null){
                    mGraphFragment = new GraphFragment();
                    mFragmentTransaction.add(R.id.main_screen_fragment_container,mGraphFragment,
                            getString(R.string.tag_fragment_graph));
                    mFragmentTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_graph));
                    mFragment.add(new FragmentTag(mGraphFragment, getString(R.string.tag_fragment_graph)));
                }
                else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_graph));
                    mFragmentTags.add(getString(R.string.tag_fragment_graph));
                }

                navMenuIndex = PROGRESS_NAV_MENU_ITEM;
                mNavigationView.getMenu().getItem(NAV_ITEM_PROGRESS_INDEX).setChecked(true);
                mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_PROGRESS_INDEX).setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_graph));
                break;
            }


            case R.id.nav_item_table:{
                if(mTableFragment == null){
                    mTableFragment = new TableFragment();
                    mFragmentTransaction.add(R.id.main_screen_fragment_container,mTableFragment,
                            getString(R.string.tag_fragment_table));
                    mFragmentTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_table));
                    mFragment.add(new FragmentTag(mTableFragment, getString(R.string.tag_fragment_table)));
                }
                else {
                    mFragmentTags.remove(getString(R.string.tag_fragment_table));
                    mFragmentTags.add(getString(R.string.tag_fragment_table));
                }
                navMenuIndex = TABLE_NAV_MENU_INDEX;
                item.setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_table));
                break;
            }
            case R.id.nav_item_calculator:{
            }
            case R.id.bottom_nav_calculator:{
                if(mCalculatorFragment == null){
                    mCalculatorFragment = new CalculatorFragment();
                    navMenuIndex = CALCULATOR_NAV_MENU_INDEX;
                    mFragmentTransaction.add(R.id.main_screen_fragment_container, mCalculatorFragment,
                            getString(R.string.tag_fragment_calculator));
                    mFragmentTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_calculator));
                    mFragment.add(new FragmentTag(mCalculatorFragment, getString(R.string.tag_fragment_calculator)));
                }
                else{
                    mFragmentTags.remove(getString(R.string.tag_fragment_calculator));
                    mFragmentTags.add(getString(R.string.tag_fragment_calculator));
                }

                navMenuIndex = CALCULATOR_NAV_MENU_INDEX;
                mNavigationView.getMenu().getItem(NAV_ITEM_CALCULATOR_INDEX).setChecked(true);
                Menu menu = mBottomNavigationViewEx.getMenu();
                mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_CALCULATOR_INDEX).setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_calculator));
                break;
            }
            case R.id.nav_item_notes :{
            }
            case R.id.bottom_nav_notes :{
                if(mNotesFragment == null){
                    mNotesFragment = new NotesFragment();
                    mFragmentTransaction.add(R.id.main_screen_fragment_container,mNotesFragment,
                            getString(R.string.tag_fragment_notes));
                    mFragmentTransaction.commit();
                    mFragmentTags.add(getString(R.string.tag_fragment_notes));
                    mFragment.add(new FragmentTag(mNotesFragment, getString(R.string.tag_fragment_notes)));
                }
                else{
                    mFragmentTags.remove(getString(R.string.tag_fragment_notes));
                    mFragmentTags.add(getString(R.string.tag_fragment_notes));
                }

                navMenuIndex = NOTES_MENU_INDEX;
                mNavigationView.getMenu().getItem(NAV_ITEM_NOTES_INDEX).setChecked(true);
                mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_NOTES_INDEX).setChecked(true);
                setFragmentVisibilities(getString(R.string.tag_fragment_notes));
                break;
            }

        }
        if(mFragment.size() > 0){
            tableFragmentNotOnTopOfStack();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void tableFragmentNotOnTopOfStack() {
        if(mTableFragment != null){
            int topOfFragment = mFragmentTags.size() - 1;
            int oneBelowTopStack = topOfFragment - 1;
            //if the table fragment is just under the top of the stack it means that it was just left
            if(mFragmentTags.get(oneBelowTopStack) == getString(R.string.tag_fragment_table)){
                //TODO implement method or interface to save the list from the active table
                mTableFragment.saveAllTables();
            }
        }
    }

    /***
     * Loop through our initialized Fragments
     * If the tag we sent is equal to one of the fragments show it
     * If not, then hide that fragment
     *
     * Should be called whenever the navigation item is selected
     * @param tag
     */
    private void setFragmentVisibilities(String tag){
        for(int i = 0; i < mFragment.size(); i++ ){
            if(tag.equals(mFragment.get(i).getTag())){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(mFragment.get(i).getFragment());
                fragmentTransaction.commit();
            }
            else{
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(mFragment.get(i).getFragment());
                fragmentTransaction.commit();
            }
            setNavigationIcons(tag);
        }
    }

    /**
     * This tag represents the fragment tag of the item we want to display
     * We need to go through our navigation items to make sure that when we move from one fragment to another our
     *  navigation views update
     * @param tag
     */
    private void setNavigationIcons(String tag) {
        if(tag.equals(getString(R.string.tag_fragment_sets))){
            mNavigationView.getMenu().getItem(NAV_ITEM_WORKOUT_INDEX).setChecked(true);
            mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_WORKOUT_INDEX).setChecked(true);
        }
        else if(tag.equals(getString(R.string.tag_fragment_table))){
            mNavigationView.getMenu().getItem(NAV_ITEM_TABLE_INDEX).setChecked(true);
        }
        else if(tag.equals(getString(R.string.tag_fragment_graph))){
            mNavigationView.getMenu().getItem(NAV_ITEM_PROGRESS_INDEX).setChecked(true);
            mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_PROGRESS_INDEX).setChecked(true);
        }
        else if(tag.equals(getString(R.string.tag_fragment_calculator))){
            mNavigationView.getMenu().getItem(NAV_ITEM_CALCULATOR_INDEX).setChecked(true);
            mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_CALCULATOR_INDEX).setChecked(true);
        }
        else if(tag.equals(getString(R.string.tag_fragment_notes))){
            mNavigationView.getMenu().getItem(NAV_ITEM_NOTES_INDEX).setChecked(true);
            mBottomNavigationViewEx.getMenu().getItem(BOTTOM_NAV_ITEM_NOTES_INDEX).setChecked(true);
        }
    }

    @Override
    public Week getWeekInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SETS_FRAGMENT_SHARED_PREFERENCE), Context.MODE_PRIVATE);
        int benchPress = sharedPreferences.getInt(getString(R.string.BENCH_PRESS_PREFERENCE),-1);
        int squat = sharedPreferences.getInt(getString(R.string.SQUAT_PREFERENCE),-1);
        int deadlift = sharedPreferences.getInt(getString(R.string.DEADLIFT_PREFERENCE),-1);
        int ohp = sharedPreferences.getInt(getString(R.string.OHP_PREFERENCE),-1);
        return new Week(-1,benchPress,squat,deadlift,ohp);
    }
}
