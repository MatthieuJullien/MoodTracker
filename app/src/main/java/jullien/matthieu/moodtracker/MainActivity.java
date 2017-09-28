package jullien.matthieu.moodtracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;

import jullien.matthieu.moodtracker.Model.History;
import jullien.matthieu.moodtracker.Model.HistoryDbHelper;
import jullien.matthieu.moodtracker.View.MoodFragment;
import jullien.matthieu.moodtracker.View.VerticalViewPager;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final int NUM_PAGES = 5;
    private static final int HAPPY_INDEX = 3;//TODO enum ?
    private static SharedPreferences mPreferences;

    // The pager widget, which handles animation and allows swiping vertically to access previous
    // and next wizard steps.
    private VerticalViewPager mPager;

    // The pager adapter, which provides the pages to the view pager widget.
    private PagerAdapter mPagerAdapter;
    // The last mood chosen for this day
    private int mCurrentMood = HAPPY_INDEX;
    private ImageView mImageNote;
    private ImageView mImageHistory;
    private String mNote = "";
    private HistoryDbHelper mDbHelper = new HistoryDbHelper(this);

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
                mPreferences = getPreferences(MODE_PRIVATE);
                mPreferences.edit().putInt("currentMood", mCurrentMood).apply();
                //TODO remettre à zéro le commentaire

                //TODO: enregistrement à faire à minuit :
                //============================
                mDbHelper.addNewDay(mCurrentMood, mNote);

                //=============================
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        mPreferences = getPreferences(MODE_PRIVATE);
        mCurrentMood = mPreferences.getInt("currentMood", HAPPY_INDEX);
        mNote = mPreferences.getString("note", null);
        mPager.setCurrentItem(mCurrentMood);
        mImageNote = findViewById(R.id.note_image);
        mImageHistory = findViewById(R.id.history_image);
        mImageNote.setOnClickListener(this);
        mImageHistory.setOnClickListener(this);
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
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mNote = input.getText().toString();
                    mPreferences = getPreferences(MODE_PRIVATE);
                    mPreferences.edit().putString("note", mNote).apply();
                }
            });
            builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (view == mImageHistory) {

            //ouvrir actvité historique
            ArrayList<History> historyList = mDbHelper.getHistory();
            for (History h : historyList) {
                System.out.println(h.toString());
            }
            //mHistoryAdapter = new HistoryAdapter(this, mHistoryList);
            //mListView.setAdapter(mHistoryAdapter);

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
