package jullien.matthieu.moodtracker.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import jullien.matthieu.moodtracker.Model.MoodInfo;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {
    MainActivity mMainActivity = null;

    public void setMainActivity(MainActivity main) {
        mMainActivity = main;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
        int mood = preferences.getInt("currentMood", MoodInfo.HAPPY_INDEX);
        String note = preferences.getString("note", null);

        HistoryDbHelper dbHelper = new HistoryDbHelper(context);
        dbHelper.addNewDay(mood, note);
        dbHelper.close();
        mMainActivity.resetMood();
    }
}
