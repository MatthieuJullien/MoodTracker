package jullien.matthieu.moodtracker.Model;


import android.provider.BaseColumns;

public class MoodDbContract {
    private MoodDbContract() {
    }

    /* Inner class that defines the table contents and queries*/
    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String MOOD_INDEX = "mood";
        public static final String DATE = "date";
        public static final String NOTE = "note";

        public static final String QUERY_TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                DATE + " INTEGER, " +
                NOTE + " TEXT," +
                MOOD_INDEX + " INTEGER )";

        public static final String QUERY_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
