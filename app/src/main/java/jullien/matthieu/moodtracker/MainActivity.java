package jullien.matthieu.moodtracker;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import jullien.matthieu.moodtracker.View.MoodFragment;
import jullien.matthieu.moodtracker.View.VerticalViewPager;

public class MainActivity extends FragmentActivity {
    private static final int NUM_PAGES = 5;
    private static final int HAPPY_INDEX = 3;//TODO enum ?

    // The pager widget, which handles animation and allows swiping vertically to access previous
    // and next wizard steps.
    private VerticalViewPager mPager;

    // The pager adapter, which provides the pages to the view pager widget.
    private PagerAdapter mPagerAdapter;
    // The last mood chosen for this day
    private int mCurrentMood = HAPPY_INDEX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);
        mPagerAdapter = new MoodPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        // Callback interface for responding to changing state of the selected page
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            // Save the mood corresponding to this page in mCurrentMood
            @Override
            public void onPageSelected(int position) {
                mCurrentMood = position;

                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                preferences.edit().putInt("currentMood", mCurrentMood).apply();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        mCurrentMood = getPreferences(MODE_PRIVATE).getInt("currentMood", HAPPY_INDEX);
        mPager.setCurrentItem(mCurrentMood);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class MoodPagerAdapter extends FragmentPagerAdapter {
        private MoodPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MoodFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
