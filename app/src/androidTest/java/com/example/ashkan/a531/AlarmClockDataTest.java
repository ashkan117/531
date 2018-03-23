package com.example.ashkan.a531;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import com.example.ashkan.a531.Activity.MainScreen;
import com.example.ashkan.a531.Data.DataManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Ashkan on 2/14/2018.
 */
@RunWith(JUnit4.class)
public class AlarmClockDataTest {

    @Rule
    public ActivityTestRule<MainScreen> mActivityTestRule = new ActivityTestRule<>(MainScreen.class);

    @Before
    public void setUp(){
        Context context = mActivityTestRule.getActivity().getApplicationContext();
        DataManager dataManager = DataManager.getInstance();
    }

    @Test
    public void addAlarmClocks(){
        onView(withId(R.id.alarm_clock_fab)).perform(click());

    }

    @Test
    public void restoreAlarmClocks(){

        onView(withId(R.id.alarm_clock_fab)).perform(click());
    }
}
