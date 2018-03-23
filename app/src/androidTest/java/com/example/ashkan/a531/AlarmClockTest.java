package com.example.ashkan.a531;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ashkan.a531.Activity.AlarmClockActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Ashkan on 1/27/2018.
 */
@RunWith(AndroidJUnit4.class)
public class AlarmClockTest {

    @Rule
    ActivityTestRule mActivityTestRule = new ActivityTestRule(AlarmClockActivity.class);

    @Test
    public void clockAppears(){

    }
}
