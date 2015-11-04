package com.abhishek.angieslist;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.abhishek.angieslist.launcher.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Abhishek on 11/4/2015.
 */
@RunWith(AndroidJUnit4.class)
public class TestActivity{


    @Rule
    public IntentsTestRule<HomeActivity> mActivityRule = new IntentsTestRule<>(HomeActivity.class);


    public void sleepFor(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Check that home activity contains a recycler view with id R.id.list_funny_images
    @Test
    public void testValidateRecyclerViewPresent(){
        sleepFor(100);
        validateRecyclerViewPresent();
        sleepFor(100);
    }

    @Test
    public void testActivityLaunch(){
        validateRecyclerViewPresent();
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(toPackage("com.abhishek.angieslist"));
        sleepFor(100);
    }

    @Test
    public void testBackButton(){
        validateRecyclerViewPresent();
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(toPackage("com.abhishek.angieslist"));
        sleepFor(100);
        pressBack();
        sleepFor(100);
        validateRecyclerViewPresent();
    }

    public void validateRecyclerViewPresent() {
        onView(withId(R.id.list_funny_images)).check(matches(notNullValue()));
    }

}
