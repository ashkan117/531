package com.example.ashkan.a531;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Set;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CustomViewPagerAdapter.OnTextChangedListener{

    private ArrayList<Integer> oneRepMaxList;
    private ViewPager tabViewPager;
    private ViewPager fragmentViewPager;
    private CustomViewPagerAdapter tabViewPagerAdapter;
    private MyFragmentPageAdapter fragmentPageAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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



        tabViewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentViewPager = (ViewPager) findViewById(R.id.fragment_view_pager);
        oneRepMaxList = new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            oneRepMaxList.add(100);
        }
        tabViewPagerAdapter = new CustomViewPagerAdapter(this, oneRepMaxList);

        fragmentPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager());
        fragmentPageAdapter.addFragment(SetsFragment.newInstance(0,oneRepMaxList));
        fragmentPageAdapter.addFragment(SetsFragment.newInstance(1,oneRepMaxList));
        fragmentPageAdapter.addFragment(SetsFragment.newInstance(2,oneRepMaxList));
        fragmentPageAdapter.addFragment(SetsFragment.newInstance(3,oneRepMaxList));

        tabViewPager.setAdapter(tabViewPagerAdapter);
        fragmentViewPager.setAdapter(fragmentPageAdapter);
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

        } else if (id == R.id.progress_nav_item) {
            Intent intent = new Intent();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public int[] onWeightEntered(int positionOfPager, ArrayList<Integer> weightEntered) {
        Log.v("MainActivity",""+oneRepMaxList.get(positionOfPager));
        tabViewPagerAdapter.replaceView(positionOfPager,oneRepMaxList);
        fragmentPageAdapter.replaceFragment(positionOfPager,oneRepMaxList);
        return new int[]{};
    }
}
