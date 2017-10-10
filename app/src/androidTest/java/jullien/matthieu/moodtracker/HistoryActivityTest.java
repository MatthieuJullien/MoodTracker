package jullien.matthieu.moodtracker;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import jullien.matthieu.moodtracker.Controller.ChartActivity;
import jullien.matthieu.moodtracker.Controller.HistoryActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class HistoryActivityTest {
    @Rule
    public ActivityTestRule<HistoryActivity> mActivityTestRule = new ActivityTestRule<>(HistoryActivity.class);
    private HistoryActivity mActivity;

    /**
     * Prepare the history activity
     */
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    /**
     * Test if a click on the chart image lauch the chart activity
     */
    @Test
    public void test_launch_chart() throws Exception {

        // register next activity that need to be monitored
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ChartActivity.class.getName(), null, false);

        onView(withId(R.id.history_piechart_image)).perform(click());

        //Wait for the activity with timeout
        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    /**
     * Close the activity
     */
    @After
    public void tearDown() throws Exception {
        mActivity.finish();
    }
}
