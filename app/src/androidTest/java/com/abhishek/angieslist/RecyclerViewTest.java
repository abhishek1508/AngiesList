package com.abhishek.angieslist;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.abhishek.angieslist.launcher.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;


/**
 * Created by Abhishek on 11/1/2015.
 */
public class RecyclerViewTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    public RecyclerViewTest() {
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

    //Test the number of items present in the recycler view adapter
    public void testItemCount(){
        sleepFor(1000);
        int jsonItems = 60; //Since each json response from Imgur funny images has 60 items
        onView(withId(R.id.list_funny_images)).check(hasItemsCount(jsonItems));
    }

    // Perform a click on first element in the RecyclerView
    public void testClickAtFirstPosition() {
        sleepFor(1000);
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    // Perform a click on 34th element in the RecyclerView
    public void testClickAtAnyPositionInFirstPage(){
        sleepFor(1500);
        //scroll to the position of the item and click it.
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.scrollToPosition(34));
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.actionOnItemAtPosition(34, click()));
    }

    // Perform a click on 75th element in the next page in RecyclerView
    public void testClickAtAnyPositionAfterFirstPage(){
        sleepFor(1000);
        //Scroll till a point when new page is loaded.
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.scrollToPosition(56));
        sleepFor(1000);
        //Click on an item from a new page.
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.actionOnItemAtPosition(75, click()));
        //getActivity().getWindow().getDecorView()
    }

    public void testIsToastDisplayed(){
        sleepFor(1000);
        //Scroll till a point when new page is loaded.
        onView(withId(R.id.list_funny_images)).perform(RecyclerViewActions.scrollToPosition(56));
        sleepFor(500);
        onView(withText(R.string.next_page)).inRoot(withDecorView(not(getActivity().getWindow().getDecorView()))) .check(matches(isDisplayed()));
    }

    public static ViewAssertion hasItemsCount(final int itemCount) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView recyclerView = (RecyclerView) view;
                if(recyclerView.getAdapter().getItemCount() == 60)
                    assertEquals(recyclerView.getAdapter().getItemCount(),itemCount);
                else
                    assertNotSame(recyclerView.getAdapter().getItemCount(),itemCount);
            }
        };
    }


}
