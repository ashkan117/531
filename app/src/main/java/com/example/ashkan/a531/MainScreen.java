package com.example.ashkan.a531;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import com.example.ashkan.a531.Activity.AlarmClockActivity;
import com.example.ashkan.a531.Activity.SettingsActivity;
import com.example.ashkan.a531.Adapters.CustomViewPagerAdapter;
import com.example.ashkan.a531.Adapters.SetFragmentPageAdapter;
import com.example.ashkan.a531.Data.DataManager;
import com.example.ashkan.a531.Data.OneRepMaxDataBaseHelper;
import com.example.ashkan.a531.Data.Week;
import com.example.ashkan.a531.Fragments.CalculatorFragment;
import com.example.ashkan.a531.Fragments.GraphFragment;
import com.example.ashkan.a531.Fragments.NotesFragment;
import com.example.ashkan.a531.Fragments.SetsFragment;
import com.example.ashkan.a531.Fragments.TableFragment;
import com.example.ashkan.a531.Fragments.WeightHelperDialogFragment;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        CustomViewPagerAdapter.OnTextChangedListener,
        GraphFragment.GetWeekInformationFromSetFragment ,
        SetsFragment.SetsFragmentEndingListener{

    private static final String BENCH_PRESS_PREFERENCE = "benchPressPreference";
    private static final String SQUAT_PREFERENCE = "sqautPreference";
    private static final String DEADLIFT_PREFERENCE = "deadliftPreference";
    private static final String OHP_PREFERENCE = "ohpPreference";
    private ArrayList<Integer> mOneRepMaxList;
    private ViewPager fragmentViewPager;
    private CustomViewPagerAdapter tabViewPagerAdapter;
    private SetFragmentPageAdapter fragmentPageAdapter;
    private TabLayout tabLayout;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    public static final String ONE_REP_MAX_LIST = "ONE_REP_MAX_LIST";
    OneRepMaxDataBaseHelper dbHelper = new OneRepMaxDataBaseHelper(this);
    private EditText oneRepMaxEditText;
    private String PREFS_NAME="MainScreenSharedPrefs";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emptyFragmentIfExists();
    }

    private void emptyFragmentIfExists() {
        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.other_fragments);
        if(currentFragment!=null){
            mFragmentManager.beginTransaction().remove(currentFragment);
        }
    }

    private ConstraintLayout tabAndViewPagerLayout;
    private int navMenuIndex = 0;
    private TabLayout navTabLayout;
    private int NOTES_MENU_INDEX =2 ;
    private int PROGRESS_NAV_MENU_ITEM=1;
    private int CALCULATOR_NAV_MENU_INDEX = 3;
    private int SETS_NAV_MENU_INDEX=0;
    private int TABLE_NAV_MENU_INDEX;
    private View otherFragmentsLayout;
    private String ACTIVE_FRAGMENT="";
    private String GRAPH_FRAGMENT_TAG="graphFragment";
    private String CALCULATOR_FRAGMENT_TAG="calculatorFragment";
    private String NOTES_FRAGMENT_TAG="notesFragment";
    private String SETS_FRAGMENT_TAG;
    private String TABLE_FRAGMENT_TAG;
    private DataManager mDataManager;
    private ContentResolver mContectResolver;

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

        mDataManager = DataManager.getInstance();

        setContentView(R.layout.activity_main_screen);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        //Only add the fragment if its empty
        Fragment fragment = mFragmentManager.findFragmentById(R.id.other_fragments);
        if(fragment==null){
            mFragmentTransaction.add(R.id.other_fragments,new SetsFragment(),SETS_FRAGMENT_TAG)
                    .commit();
        }


    }

    private void loadSharedPreferences(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,0);
        if(preferences.getInt(BENCH_PRESS_PREFERENCE,-1)!=-1){
            mOneRepMaxList.add(preferences.getInt(BENCH_PRESS_PREFERENCE,-1));
            mOneRepMaxList.add(preferences.getInt(SQUAT_PREFERENCE,-1));
            mOneRepMaxList.add(preferences.getInt(DEADLIFT_PREFERENCE,-1));
            mOneRepMaxList.add(preferences.getInt(OHP_PREFERENCE,-1));
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
        navTabLayout = (TabLayout) findViewById(R.id.nav_tab_layout);
        navTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                performProperTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                performProperTab(tab);
            }
        });

        invalidateOptionsMenu();

        otherFragmentsLayout = findViewById(R.id.other_fragments);

    }

    private void performProperTab(TabLayout.Tab tab) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (tab.getText().toString()){
            case "Progress" :
                navMenuIndex= PROGRESS_NAV_MENU_ITEM;
                //hideSetView();
                mFragmentTransaction.replace(R.id.other_fragments,new GraphFragment(), GRAPH_FRAGMENT_TAG);
                mFragmentTransaction.commit();
                break;
            case "Calculator":
                navMenuIndex= CALCULATOR_NAV_MENU_INDEX;
                //hideSetView();
                mFragmentTransaction.replace(R.id.other_fragments,new CalculatorFragment(), CALCULATOR_FRAGMENT_TAG);
                mFragmentTransaction.commit();
                break;
            case "Notes" :
                navMenuIndex= NOTES_MENU_INDEX;
                //hideSetView();
                //mFragmentTransaction = mFragmentManager.beginTransaction();
                //removes fragment from the Id
                //TODO: Might be solution to the ackwkard layout in main screen
                //mFragmentManager.beginTransaction().remove(mFragmentManager.findFragmentById(R.id.main_container)).commit();
//                mFragmentManager.executePendingTransactions();
                NOTES_FRAGMENT_TAG = "NoteFragment";
                mFragmentTransaction.replace(R.id.other_fragments,new NotesFragment(), NOTES_FRAGMENT_TAG);
                mFragmentTransaction.commit();
                break;
            case "Workout":
                navMenuIndex= SETS_NAV_MENU_INDEX;
                //hideSetView();
                SETS_FRAGMENT_TAG = "SetsFragment";
                mFragmentTransaction.replace(R.id.other_fragments,new SetsFragment(), SETS_FRAGMENT_TAG);
                mFragmentTransaction.commit();
                break;
        }
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
        if (id == R.id.sets_action_notifications) {
            Intent intent = new Intent(this,AlarmClockActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.sets_action_settings){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.weight_help_setting){
            WeightHelperDialogFragment dialogFragment = new WeightHelperDialogFragment();
            dialogFragment.show(getFragmentManager(),"weightHelperDialogFragment");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        mFragmentTransaction = mFragmentManager.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.weights_nav_item) {
            // Handle the action
            navMenuIndex=SETS_NAV_MENU_INDEX;
            mFragmentTransaction.replace(R.id.other_fragments,new SetsFragment(),SETS_FRAGMENT_TAG);
            mFragmentTransaction.commit();
            //showSetView();

        }
        else if (id == R.id.progress_nav_item) {
            //SetView();
            mFragmentTransaction.replace(R.id.other_fragments,new GraphFragment(),GRAPH_FRAGMENT_TAG);
            mFragmentTransaction.commit();
            navMenuIndex=PROGRESS_NAV_MENU_ITEM;
        }
        else if(id==R.id.table_nav_item){
            //hideSetView();
            TABLE_FRAGMENT_TAG = "TableFragment";
            mFragmentTransaction.replace(R.id.other_fragments,new TableFragment(), TABLE_FRAGMENT_TAG);
            mFragmentTransaction.commit();
            navMenuIndex= TABLE_NAV_MENU_INDEX;
        }
        else if(id==R.id.calculator_nav_item){
            //hideSetView();
            android.app.Fragment fragment = getFragmentManager().findFragmentById(R.id.other_fragments);
            //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.other_fragments)).commit();
            mFragmentTransaction.replace(R.id.other_fragments, new CalculatorFragment(),CALCULATOR_FRAGMENT_TAG);
            mFragmentTransaction.commit();
            navMenuIndex=CALCULATOR_NAV_MENU_INDEX;

        }
        else if(id==R.id.notes_nav_item){

            navMenuIndex=NOTES_MENU_INDEX;
            //hideSetView();
            //removes fragment from the Id
            //TODO: Might be solution to the awkard layout in main screen
            mFragmentTransaction.replace(R.id.other_fragments,new NotesFragment(),NOTES_FRAGMENT_TAG);
            Fragment frag = mFragmentManager.findFragmentById(R.id.other_fragments);
            mFragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        editor.putInt(BENCH_PRESS_PREFERENCE,mOneRepMaxList.get(0));
        editor.putInt(SQUAT_PREFERENCE,mOneRepMaxList.get(1));
        editor.putInt(DEADLIFT_PREFERENCE,mOneRepMaxList.get(2));
        editor.putInt(OHP_PREFERENCE,mOneRepMaxList.get(3));
        editor.commit();
        emptyFragmentIfExists();
    }


    @Override
    public Week getWeekInfo() {
        int weekNumber=-1;
        int bench = mOneRepMaxList.get(0);
        int squat = mOneRepMaxList.get(1);
        int deadlift = mOneRepMaxList.get(2);
        int ohp = mOneRepMaxList.get(3);
        return new Week(weekNumber,bench,squat,deadlift,ohp);
    }


    @Override
    public void removeFragmentFromMainScreen() {
        Fragment setsFragment = mFragmentManager.findFragmentById(R.id.other_fragments);
        if(setsFragment instanceof SetsFragment){
            mFragmentTransaction = mFragmentManager.beginTransaction().remove(setsFragment);
            mFragmentTransaction.commit();
        }

    }
}
