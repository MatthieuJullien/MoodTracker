package jullien.matthieu.moodtracker.Controller;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

import jullien.matthieu.moodtracker.Model.MoodInfo;
import jullien.matthieu.moodtracker.R;
import jullien.matthieu.moodtracker.View.MoodFragment;
import jullien.matthieu.moodtracker.View.VerticalViewPager;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static SharedPreferences mPreferences;

    // The pager widget, which handles animation and allows swiping vertically
    private VerticalViewPager mPager;

    // The last mood chosen for this day. By default, set to happy.
    private int mCurrentMood = MoodInfo.HAPPY_INDEX;

    private ImageView mImageNote;
    private ImageView mImageHistory;
    private String mNote = "";
    private AlarmReceiver mAlarmReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get current mood and note
        mPreferences = getSharedPreferences("data", MODE_PRIVATE);
        mCurrentMood = mPreferences.getInt("currentMood", MoodInfo.HAPPY_INDEX);
        mNote = mPreferences.getString("note", null);

        // Create an alarm to save the app state (mood and note) at midnight everyday
        setAlarm();

        mPager = findViewById(R.id.pager);

        // The pager adapter, which provides the pages to the view pager widget.
        PagerAdapter mPagerAdapter = new MoodPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        // Callback interface for responding to changing state of the selected page
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            // Save the mood corresponding to this page in mCurrentMood
            @Override
            public void onPageSelected(int position) {
                mCurrentMood = position;
                mPreferences.edit().putInt("currentMood", mCurrentMood).apply();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });


        // Set the current page to the current mood
        mPager.setCurrentItem(mCurrentMood);

        mImageNote = findViewById(R.id.note_image);
        mImageHistory = findViewById(R.id.history_image);
        mImageNote.setOnClickListener(this);
        mImageHistory.setOnClickListener(this);
    }

    private void resetMood() {
        mCurrentMood = MoodInfo.HAPPY_INDEX;
        mNote = null;
        mPreferences.edit().putInt("currentMood", mCurrentMood).apply();
        mPreferences.edit().putString("note", mNote).apply();
        mPager.setCurrentItem(mCurrentMood);
        Toast.makeText(MainActivity.this, "Une nouvelle journée débute...", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0); // midnight

        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000 * 10, pendingIntent);
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

    @Override
    public void onClick(View view) {
        if (view == mImageNote) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Commentaire");

            // Set up the input
            final EditText input = new EditText(this);

            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Save the note
                    mNote = input.getText().toString();
                    mPreferences.edit().putString("note", mNote).apply();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (view == mImageHistory) {
            Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(historyActivity);
        }
    }

    // FragmentPageAdapter which provide the pages as fragments
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
            return MoodInfo.NB_MOOD;
        }
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
            int mood = preferences.getInt("currentMood", MoodInfo.HAPPY_INDEX);
            String note = preferences.getString("note", null);

            System.out.println("Mood = " + mood + " : " + note);
            HistoryDbHelper dbHelper = new HistoryDbHelper(context);
            dbHelper.addNewDay(mood, note);
            dbHelper.close();
           // resetMood();
        }
    }
}
