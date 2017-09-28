package jullien.matthieu.moodtracker.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HistoryDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "MoodTracker.db";

    public HistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MoodDbContract.HistoryEntry.QUERY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(MoodDbContract.HistoryEntry.QUERY_DELETE_TABLE);
        onCreate(db);
    }

    public void addNewDay(int currentMood, String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MoodDbContract.HistoryEntry.MOOD_INDEX, currentMood);
        values.put(MoodDbContract.HistoryEntry.DATE, Calendar.getInstance().getTime().getTime());
        values.put(MoodDbContract.HistoryEntry.NOTE, note);

        // Insert the new row, returning the primary key value of the new row
        db.insert(MoodDbContract.HistoryEntry.TABLE_NAME, null, values);
        //TODO delete first id (limit 1) si taille > 7 ou 7 si taille < 14
    }

    public ArrayList<History> getHistory() {
        ArrayList<History> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM " + MoodDbContract.HistoryEntry.TABLE_NAME +
                        " ORDER BY " + MoodDbContract.HistoryEntry._ID +
                        " DESC LIMIT 7;", null);
        if (c.moveToLast()) {
            do {
                History history = new History();
                history.setId(c.getLong(c.getColumnIndex(MoodDbContract.HistoryEntry._ID)));
                history.setMoodIndex(c.getInt(c.getColumnIndex(MoodDbContract.HistoryEntry.MOOD_INDEX)));
                history.setDate(new Date(c.getLong(c.getColumnIndex(MoodDbContract.HistoryEntry.DATE))));
                history.setNote(c.getString(c.getColumnIndex(MoodDbContract.HistoryEntry.NOTE)));
                historyList.add(history);
            } while (c.moveToPrevious());
        }
        c.close();
        return historyList;
    }
}
