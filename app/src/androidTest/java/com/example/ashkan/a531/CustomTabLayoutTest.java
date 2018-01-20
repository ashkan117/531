package com.example.ashkan.a531;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.ashkan.a531.Fragments.SetsFragment;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.R.attr.id;
import static android.R.attr.text;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.ViewAction.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*;
/**
 * Created by Ashkan on 1/17/2018.
 */
@RunWith(AndroidJUnit4.class)
public class CustomTabLayoutTest {

    @Rule
    public ActivityTestRule<MainScreen> mSetsFragmentActivityTestRule = new ActivityTestRule<>(MainScreen.class);
    public FragmentTransaction mFragmentTransaction;

    @Before
    public void init(){
        mFragmentTransaction = mSetsFragmentActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

    }

    @Test
    public void checkTabLayoutDisplayed() {
        onView(withId(R.id.nav_tab_layout))
                .perform(click())
                .check(matches(isDisplayed()));
        ViewInteraction appCompatTextView = onView(
                allOf(withText("Workout"), isDisplayed()));
        appCompatTextView.perform(click());
    }

    public void makeClickable(Matcher<View> matcher) {
        onView(matcher).check(matches(allOf(isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click plus button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
    }
    @Test
    public void navigationMenuSetsPressed(){
        Matcher<View> matcher = withText("Workout");
        //TODO: displayed makes it work???
        //Note: isDisplayed will select views that are partially displayed
        onView(allOf(withText("Workout"), isDisplayed()))
            .perform(click());
        onView(withId(R.id.set_fragment_view_pager)).check(matches(isDisplayed()));
        onView(withId(R.id.set_tab_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void setsFragmentCreated(){

    }

}
