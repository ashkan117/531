package com.example.ashkan.a531;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.ashkan.a531.Activity.MainScreen;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainScreenTest {

    @Rule
    public ActivityTestRule<MainScreen> mActivityTestRule = new ActivityTestRule<>(MainScreen.class);

    //@Rule
   // public IntentsTestRule<MainScreen> intentsTestRule = new IntentsTestRule<>(MainScreen.class);

    @Test
    public void mainScreenStartUp(){
        onView(withId(R.id.main_screen_fragment_container)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.set_fragment_view_pager)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void mainScreenTest() {
        /*
        ViewInteraction appCompatTextView = onView(
                allOf(withText("Workout"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withText("Calculator"), isDisplayed()));
        appCompatTextView2.perform(click());
        */
    }

}
