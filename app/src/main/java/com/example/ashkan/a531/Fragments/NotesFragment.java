package com.example.ashkan.a531.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.ashkan.a531.Adapters.NotesViewPagerAdapter;
import com.example.ashkan.a531.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends DialogFragment {


    private static final String TAG = NotesFragment.class.getSimpleName();
    private static final String NOTES_SHARED_PREFERENCES = "NotesSharedPreference";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NotesViewPagerAdapter mPagerAdapter;
    private String NOTES_ID ="NotePreferencesString";

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_notes_menu,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int currentItem;
        String note;
        switch (item.getItemId()){
            case R.id.save_note_menu_item:
                //DataManager.saveNote(currentItem,dbHelper);
                Toast.makeText(getContext(),"Saved Note!",Toast.LENGTH_SHORT)
                        .show();
                saveNoteAsPreference();
                closeKeyBoard();
                return true;
            default:
                return false;
        }

    }

    public void closeKeyBoard()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    public void onPause() {
        super.onPause();
        saveAllNotes();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveAllNotes();
    }

    private void saveAllNotes() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NOTES_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i=0;i<mPagerAdapter.getCount();i++){
//            mViewPager.setCurrentItem(i,false);
            NoteItemFragment noteItemFragment = (NoteItemFragment) mPagerAdapter.getItem(i);
            if(noteItemFragment.getNote()!=null){
                String note = noteItemFragment.getNote();
                editor.putString(NOTES_ID+i,note);
            }
        }
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

        //GraphFragmentPagerAdapter pagerAdapter = new GraphFragmentPagerAdapter(manager);
        //returns a private FragmentManager for placing and managing Fragments inside of this Fragment.
        FragmentManager manager = getChildFragmentManager();

        Drawable[] images = getNotesDrawableImages();
        mPagerAdapter = new NotesViewPagerAdapter(manager);

        ArrayList<String> stringArrayList = reloadSharedPreferenceNotes();
        createFragmentsForViewPager(stringArrayList);

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager,true);

        setUpNotesTab(images);
        mTabLayout.bringToFront();
        mTabLayout.requestFocus();
       return rootView;
    }

    private void createFragmentsForViewPager(ArrayList<String> stringArrayList) {
        String item1 = stringArrayList.get(0);
        String item2 = stringArrayList.get(1);
        String item3 = stringArrayList.get(2);
        String item4 = stringArrayList.get(3);

        mPagerAdapter.addFragment(0,NoteItemFragment.newInstance(item1));
        mPagerAdapter.addFragment(1,NoteItemFragment.newInstance(item2));
        mPagerAdapter.addFragment(2,NoteItemFragment.newInstance(item3));
        mPagerAdapter.addFragment(3,NoteItemFragment.newInstance(item4));
    }

    private ArrayList<String> reloadSharedPreferenceNotes() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NOTES_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        //only need editor if we're changing sharedPrefs
        String note;
        Bundle debug = getArguments();
        int count = mPagerAdapter.getCount();
        for(int i=0;i<count;i++){
            note = sharedPreferences.getString(NOTES_ID +i,"");
            stringArrayList.add(note);
        }
        return stringArrayList;
    }

    private void setUpNotesTab(Drawable[] images) {
        //TODO: when you set up a tablayout with a view pager the adapter is expected to handle the title and such
        // This is a little more difficult when we also have icons so a solution is
        //  to update the tiles after the setUpWithViewPager
        String[] titles = new String[]{"Bench Press","Squat","Deadlift","Overhead Press"};
        for(int i=0;i<4;i++){
            mTabLayout.getTabAt(i).setText(titles[i]);
            //If I want to add icon change here
            //mTabLayout.getTabAt(i).setIcon(images[i]);
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
