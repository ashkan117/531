package com.example.ashkan.a531;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.Gravity;

import com.example.ashkan.a531.Activity.MainScreen;
import com.example.ashkan.a531.Data.DataManager;
import com.example.ashkan.a531.Data.OneRepMaxDataBaseHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Ashkan on 2/11/2018.
 */

@RunWith(JUnit4.class)
public class TableFragmentTest {

    @Rule
    public ActivityTestRule<MainScreen> mActivityTestRule = new ActivityTestRule<>(MainScreen.class);
    private DataManager mDataManager;

    @Before
    public void setUp(){

        //mDataManager = DataManager.getInstance(new OneRepMaxDataBaseHelper(mActivityTestRule.getActivity().getApplicationContext()));
        //TODO: testing navigation drawer clicks

        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open()); // Open Drawer

        // Start the screen of your activity.
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_item_table));

        onView(allOf(withText("Bench Press"),hasSibling(withText("Deadlift"))));

    }

    @Test
    public void testFabButton(){

        onView(withId(R.id.fab_table_fragment)).perform(click());

  //      onView(withId(R.id.table_recycler_view)).perform(RecyclerViewActions.scrollToHolder(getViewHolder()));

        onView(allOf(withId(R.id.bench_press_max_edit_text_view),withText("0")));

        int weekSize = mDataManager.getWeekSize(new OneRepMaxDataBaseHelper(mActivityTestRule.getActivity().getApplicationContext()));


        onView(withId(R.id.table_recycler_view)).perform(RecyclerViewActions.scrollTo(
                (allOf(withId(R.id.week_number_text_view),withText("Week "+weekSize)))
        ));

        onView(allOf(withId(R.id.bench_press_max_edit_text_view),withText("0"))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.bench_press_max_edit_text_view),withText("0"))).perform(swipeRight());
    }

    @Test
    public void incrementingWeekNumber(){
        int weekSize = mDataManager.getWeekSize(new OneRepMaxDataBaseHelper(mActivityTestRule.getActivity().getApplicationContext()));
        onView(withId(R.id.fab_table_fragment)).perform(click());
        onView(withId(R.id.table_recycler_view)).perform(RecyclerViewActions.scrollToPosition(weekSize));
        onView(withId(R.id.week_number_text_view)).check(matches(withText("Week "+weekSize+":")));

    }

}
