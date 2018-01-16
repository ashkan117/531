package com.example.ashkan.a531.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashkan.a531.Adapters.NotesViewPagerAdapter;
import com.example.ashkan.a531.Data.DataManager;
import com.example.ashkan.a531.Data.OneRepMaxDataBaseHelper;
import com.example.ashkan.a531.R;

import java.util.ArrayList;
import java.util.Set;

import static android.R.id.list;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends DialogFragment {


    private static final String NOTES_SHARED_PREFERENCES = "NotesSharedPreference";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NotesViewPagerAdapter mPagerAdapter;
    private OneRepMaxDataBaseHelper dbHelper;
    private String NOTES_ID ="NotePreferencesString";

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_screen,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int currentItem;
        String note;
        switch (item.getItemId()){
            case R.id.save_note_menu_item:
                //DataManager.saveNote(currentItem,dbHelper);
                saveNoteAsPreference();
                return true;
            default:
                return false;
        }

    }

    private void saveNoteAsPreference() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NOTES_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int currentItem = mViewPager.getCurrentItem();
        NoteItemFragment noteItemFragment = (NoteItemFragment) mPagerAdapter.getItem(currentItem);
        String note = noteItemFragment.getNote();
        editor.putString(NOTES_ID+currentItem,note);
        //Don't forget to commit
        editor.commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notes,null,false);
        mTabLayout = (TabLayout)rootView.findViewById(R.id.notes_tab_layout);

        mViewPager = (ViewPager)rootView.findViewById(R.id.notes_view_pager);
        dbHelper = new OneRepMaxDataBaseHelper(getContext());
        //TODO: FIgure out why it's getChildFragmentManager
        FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        //GraphFragmentPagerAdapter pagerAdapter = new GraphFragmentPagerAdapter(manager);

        Drawable[] images = getNotesDrawableImages();
        mPagerAdapter = new NotesViewPagerAdapter(manager);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NOTES_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        //only need editor if we're changing sharedPrefs
        ArrayList<String> stringArrayList = new ArrayList<>();
        String note;
        Bundle debug = getArguments();
        int count = mPagerAdapter.getCount();
        for(int i=0;i<count;i++){
            note = sharedPreferences.getString(NOTES_ID +i,"");
            stringArrayList.add(note);
        }
        String item1 = stringArrayList.get(0);
        String item2 = stringArrayList.get(1);
        String item3 = stringArrayList.get(2);
        String item4 = stringArrayList.get(3);
        mPagerAdapter.addFragment(0,NoteItemFragment.newInstance(item1));
        mPagerAdapter.addFragment(1,NoteItemFragment.newInstance(item2));
        mPagerAdapter.addFragment(2,NoteItemFragment.newInstance(item3));
        mPagerAdapter.addFragment(3,NoteItemFragment.newInstance(item4));
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager,true);
        setUpNotesTab(images);
        mTabLayout.requestFocus();
        return rootView;
    }

    private void setUpNotesTab(Drawable[] images) {
        //TODO: when you set up a tablayout with a view pager the adapter is expected to handle the title and such
        // This is a little more difficult when we also have icons so a solution is
        //  to update the tiles after the setUpWithViewPager
        String[] titles = new String[]{"Bench Press","Squat","Deadlift","Overhead Press"};
        for(int i=0;i<4;i++){
            mTabLayout.getTabAt(i).setText(titles[i]);
            mTabLayout.getTabAt(i).setIcon(images[i]);
        }
    }

    private Drawable[] getNotesDrawableImages() {
        Drawable[] images = new Drawable[4];
        images[0]=getResources().getDrawable(R.drawable.ic_menu_slideshow,null);
        images[1]=getResources().getDrawable(R.drawable.ic_menu_slideshow,null);
        images[2]=getResources().getDrawable(R.drawable.ic_menu_slideshow,null);
        images[3]=getResources().getDrawable(R.drawable.ic_menu_slideshow,null);
        return images;
    }
}
