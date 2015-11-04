package com.abhishek.angieslist;

import android.content.res.Configuration;
import android.test.ActivityInstrumentationTestCase2;

import com.abhishek.angieslist.launcher.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by Abhishek on 11/4/2015.
 */
public class OrientationChangeTest extends ActivityInstrumentationTestCase2<HomeActivity> {


    public OrientationChangeTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void sleepFor(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testOrientationChange(){
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
        sleepFor(1000);
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        sleepFor(1000);
    }
}
