package com.example.ashkan.a531;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.ashkan.a531.Activity.MainScreen;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Ashkan on 2/10/2018.
 */

@RunWith(AndroidJUnit4.class)
public class SetFragmentTest {

    @Rule
    //delays starting activity
    public ActivityTestRule<MainScreen> mActivityTestRule = new ActivityTestRule<>(MainScreen.class,true,false);
    private SharedPreferences.Editor preferencesEditor;

    @Before
    public void setUp() {
        Context context = getInstrumentation().getTargetContext();

        // create a SharedPreferences editor
        preferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    @Test
    public void savingEditText(){
        String zero = "ONE_REP_MAX_LIST"+0;
        String one = "ONE_REP_MAX_LIST"+1;
        String two = "ONE_REP_MAX_LIST"+2;
        String three = "ONE_REP_MAX_LIST"+3;

        // Set SharedPreferences data
        preferencesEditor.putInt(zero, 100);
        preferencesEditor.putInt(one, 125);
        preferencesEditor.putInt(two, 150);
        preferencesEditor.putInt(three, 175);
        preferencesEditor.commit();

        // Launch activity
        mActivityTestRule.launchActivity(new Intent());

        onView(allOf(
                withId(R.id.weight_edit_text_view),
                hasSibling(allOf(withId(R.id.type_of_exercise_text_view),withText("Bench Press")))
        )).check(matches(withText("100")));

        onView(allOf(
                withId(R.id.weight_edit_text_view),
                hasSibling(allOf(withId(R.id.type_of_exercise_text_view),withText("Squat")))
        )).check(matches(withText("125")));

        onView(allOf(
                withId(R.id.weight_edit_text_view),
                hasSibling(allOf(withId(R.id.type_of_exercise_text_view),withText("Deadlift")))
        )).check(matches(withText("150")));

        onView(allOf(
                withId(R.id.weight_edit_text_view),
                hasSibling(allOf(withId(R.id.type_of_exercise_text_view),withText("Overhead Press")))
        )).check(matches(withText("175")));




    }

    @Test
    public void changingExerciseBenchToSquat(){

        //TODO: i can check if a tab is selected by checking if a child view inside of it is selected (possible has window focus/focus)
        onView(withId(R.id.set_tab_layout)).check(matches(isCompletelyDisplayed()));
        //checkSwitch("Bench Press","Squat");
        //checkSwitch("Bench Press","Deadlift");
        //checkSwitch("Bench Press","Overhead Press");

    }


    private void checkSwitch(String start, String finish) {

        //Make sure we start on start
        onView(allOf(
                withId(R.id.type_of_exercise_text_view),
                hasSibling(allOf(withId(R.id.weight_edit_text_view))),
                withText(start)
        )).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isEnabled(); // no constraints, they are checked above
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

        onView(allOf(
                withId(R.id.type_of_exercise_text_view),
                hasSibling(allOf(withId(R.id.weight_edit_text_view))),
                withText(start)
        )).check(matches(isSelected()));

        onView(allOf(
                withId(R.id.type_of_exercise_text_view),
                hasSibling(allOf(withId(R.id.weight_edit_text_view))),
                withText(finish)
        )).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isEnabled(); // no constraints, they are checked above
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
        onView(allOf(
                withId(R.id.type_of_exercise_text_view),
                hasSibling(allOf(withId(R.id.weight_edit_text_view))),
                withText(finish)
        )).check(matches(isSelected()));
    }

    @Test
    public void benchPressWeight(){
        onView(withId(R.id.set_tab_layout)).check(matches(isCompletelyDisplayed()));
        onView(allOf(
                withId(R.id.weight_edit_text_view),
                hasSibling(allOf(withId(R.id.type_of_exercise_text_view),withText("Bench Press")))
        )).perform(clearText(),typeText("150"),pressImeActionButton());

        onView(allOf(withId(R.id.weight_text_view),
                hasSibling(allOf(withId(R.id.set_number_text_view), withText("Set 1")))
                ,hasSibling(allOf(withId(R.id.percentage_of_weight_text_view),withText("75%")))
                ,isDisplayed())
        )
                .check(matches(withText("115 * 5")));

        onView(allOf(withId(R.id.weight_text_view),
                hasSibling(allOf(withId(R.id.set_number_text_view), withText("Set 3")))
                ,hasSibling(allOf(withId(R.id.percentage_of_weight_text_view),withText("95%")))
                ,isDisplayed())
        )
                .check(matches(withText("145 * 1+")));
    }
}
