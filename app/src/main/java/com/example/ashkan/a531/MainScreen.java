package com.example.ashkan.a531;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ashkan.a531.Adapters.CustomViewPagerAdapter;
import com.example.ashkan.a531.Adapters.MyFragmentPageAdapter;
import com.example.ashkan.a531.Data.DataManager;
import com.example.ashkan.a531.Data.OneRepMaxDataBaseHelper;
import com.example.ashkan.a531.Data.Week;
import com.example.ashkan.a531.Fragments.CalculatorFragment;
import com.example.ashkan.a531.Fragments.GraphFragment;
import com.example.ashkan.a531.Fragments.GraphPointDialogFragment;
import com.example.ashkan.a531.Fragments.SetsFragment;
import com.example.ashkan.a531.Fragments.TableFragment;

import java.util.ArrayList;

import static android.R.attr.y;
import static android.R.id.edit;
import static android.os.Build.VERSION_CODES.M;
import static android.view.View.GONE;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        CustomViewPagerAdapter.OnTextChangedListener,MyFragmentPageAdapter.UpdateTabLayout,
        GraphFragment.GetWeekInformationFromSetFragment
        ,SetsFragment.OnSetsFragmentRecoveredStateListener{

    private static final String BENCH_PRESS_PREFERENCE = "benchPressPreference";
    private static final String SQUAT_PREFERENCE = "sqautPreference";
    private static final String DEADLIFT_PREFERENCE = "deadliftPreference";
    private static final String OHP_PREFERENCE = "ohpPreference";
    private ArrayList<Integer> mOneRepMaxList;
    private ViewPager fragmentViewPager;
    private CustomViewPagerAdapter tabViewPagerAdapter;
    private MyFragmentPageAdapter fragmentPageAdapter;
    private TabLayout tabLayout;
    private android.app.FragmentManager fragmanager;
    private android.app.FragmentTransaction mFragmentTransaction;
    public static final String ONE_REP_MAX_LIST = "ONE_REP_MAX_LIST";
    OneRepMaxDataBaseHelper dbHelper = new OneRepMaxDataBaseHelper(this);
    private EditText oneRepMaxEditText;
    private String PREFS_NAME="MainScreenSharedPrefs";
    private FragmentManager supportFragmentManager;
    private FragmentTransaction mSupportFragmentTransaction;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putIntegerArrayList(ONE_REP_MAX_LIST,mOneRepMaxList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MainScreen","Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mOneRepMaxList = new ArrayList<>();

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
        fragmanager = getFragmentManager();
        supportFragmentManager =getSupportFragmentManager();

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        fragmentViewPager = (ViewPager) findViewById(R.id.fragment_view_pager);

        tabViewPagerAdapter = new CustomViewPagerAdapter(this, mOneRepMaxList);

        fragmentPageAdapter = new MyFragmentPageAdapter(this,getSupportFragmentManager(),tabLayout);

        fragmentPageAdapter.addFragment(SetsFragment.newInstance(0, mOneRepMaxList));
        fragmentPageAdapter.addFragment(SetsFragment.newInstance(1, mOneRepMaxList));
        fragmentPageAdapter.addFragment(SetsFragment.newInstance(2, mOneRepMaxList));
        fragmentPageAdapter.addFragment(SetsFragment.newInstance(3, mOneRepMaxList));


        fragmentViewPager.setAdapter(fragmentPageAdapter);
        tabLayout.setupWithViewPager(fragmentViewPager,true);
        setUpCustomTabView();
        // Iterate over all tabs and set the custom view



    }

    private void setUpCustomTabView() {

        //Sets up our tabs
        setUpCustomTabs(0,"Bench Press");
        setUpCustomTabs(1,"Squat");
        setUpCustomTabs(2,"Deadlift");
        setUpCustomTabs(3,"Overhead Press");

        //gets focus to just focus on the tablayout
        //otherwise the edittext always grabs focus on the last item
        findViewById(R.id.tab_layout).requestFocus();
        tabLayout.setScrollPosition(0,0f,true);
        fragmentViewPager.setCurrentItem(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setUpCustomTabView();
//        repopulateTabs(0,mOneRepMaxList);
//        repopulateTabs(1,mOneRepMaxList);
//        repopulateTabs(2,mOneRepMaxList);
//        repopulateTabs(3,mOneRepMaxList);
    }

    private void setUpCustomTabs(final int position, String exerciseType) {

        final ConstraintLayout tabGroupView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.custom_tab_item,null);
        final EditText oneRepMaxEditText = (EditText) tabGroupView.findViewById(R.id.weight_edit_text_view);
        TextView title = (TextView) tabGroupView.findViewById(R.id.type_of_exercise_text_view);

        //oneRepMaxEditText.setText("100");
        //mOneRepMaxList.size();
        title.setText(exerciseType);
        tabLayout.getTabAt(position).setText("TITLE?");
        oneRepMaxEditText.setText(mOneRepMaxList.get(position).toString());
        String input=oneRepMaxEditText.getText().toString();
        oneRepMaxEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    Log.v("CustomViewPageAdapter","newWEight entered is "+text);
                    mOneRepMaxList.set(position,Integer.parseInt(text));
                    //SetsFragment setsFragment= SetsFragment.newInstance(position,mOneRepMaxList);

                    fragmentPageAdapter.replaceFragment(position, mOneRepMaxList);

                    return true;
                }
                return false;
            }
        });

        tabLayout.getTabAt(position).setCustomView(tabGroupView);

    }

    public void closeKeyBoard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.weights_nav_item) {
            // Handle the action
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.main_container);
            frameLayout.setVisibility(View.GONE);

        }
        else if (id == R.id.progress_nav_item) {
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.main_container);
            frameLayout.setVisibility(View.VISIBLE);
//            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//            frameLayout.setLayoutParams(layoutParams);
            mFragmentTransaction = fragmanager.beginTransaction();
            mFragmentTransaction.replace(R.id.main_container,new GraphFragment(),"GraphFragment");
            mFragmentTransaction.commit();
//            tabLayout.setVisibility(GONE);
//            fragmentViewPager.setVisibility(GONE);

        }
        else if(id==R.id.table_nav_item){
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.main_container);
            frameLayout.setVisibility(View.VISIBLE);
            mFragmentTransaction = fragmanager.beginTransaction();
            mFragmentTransaction.replace(R.id.main_container,new TableFragment(),"TableFragment");
            mFragmentTransaction.commit();
        }
        else if(id==R.id.calculator_nav_item){

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.main_container);
            frameLayout.setVisibility(View.VISIBLE);
            mSupportFragmentTransaction = supportFragmentManager.beginTransaction();
            mSupportFragmentTransaction.replace(R.id.main_container, new CalculatorFragment(),"Calculator");
            mSupportFragmentTransaction.commit();
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
    public void updateTabLayout(int position, ArrayList<Integer> oneRepMaxList) {
        setUpCustomTabs(0,"Bench Press",oneRepMaxList);
        setUpCustomTabs(1,"Squat",oneRepMaxList);
        setUpCustomTabs(2,"Deadlift",oneRepMaxList);
        setUpCustomTabs(3,"Overhead Press",oneRepMaxList);

        //again we need to make sure that the edittext doesnt force getting the focus
        closeKeyBoard();
        tabLayout.setScrollPosition(position,0f,true);
        fragmentViewPager.setCurrentItem(position);
        fragmentViewPager.requestFocus();
    }

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
    }

    private void setUpCustomTabs(final int position, String exerciseType, ArrayList<Integer> oneRepMaxList) {

        final ConstraintLayout tabGroupView = (ConstraintLayout) getLayoutInflater().inflate(R.layout.custom_tab_item,null);
        EditText oneRepMaxEditText = (EditText) tabGroupView.findViewById(R.id.weight_edit_text_view);
        TextView title = (TextView) tabGroupView.findViewById(R.id.type_of_exercise_text_view);

        oneRepMaxEditText.setText(String.valueOf(oneRepMaxList.get(position)));
        String tabString=oneRepMaxEditText.getText().toString();
        title.setText(exerciseType);
        //tabLayout.getTabAt(position).setText("TITLE?");

        oneRepMaxEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    Log.v("CustomViewPageAdapter","newWEight entered is "+text);
                    mOneRepMaxList.set(position,Integer.parseInt(text));
                    //SetsFragment setsFragment= SetsFragment.newInstance(position,mOneRepMaxList);
                    fragmentPageAdapter.replaceFragment(position, mOneRepMaxList);
                    //DataManager.saveTabEditText(mOneRepMaxList);
                    return true;
                }
                return false;
            }
        });


        tabLayout.getTabAt(position).setCustomView(tabGroupView);



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
    public void repopulateTabs(int position,ArrayList<Integer> oneRepMaxList) {
        Log.v("Repop","repop");
        View customView=tabLayout.getTabAt(position).getCustomView();
        EditText editText= (EditText) customView.findViewById(R.id.weight_edit_text_view);
        editText.setText(oneRepMaxList.get(position).toString());
        String text = editText.getText().toString();
        tabLayout.getTabAt(position).setCustomView(customView);

        //tabLayout.removeAllTabs();
//        updateTabLayout(position,oneRepMaxList);
//        View rootViewCustomTab = getLayoutInflater().inflate(R.layout.custom_tab_item,null);
//        TextView textView = (TextView) rootViewCustomTab.findViewById(R.id.type_of_exercise_text_view);
//        EditText editText = (EditText) rootViewCustomTab.findViewById(R.id.weight_edit_text_view);
//        textView.setText("Bench");
//        editText.setText(oneRepMaxList.get(position).toString());
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(fragmentPageAdapter.getTabView(i,mOneRepMaxList));
//        }
    }
}
