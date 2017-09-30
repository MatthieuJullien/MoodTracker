package jullien.matthieu.moodtracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class History {
    private long mId;
    private int mMoodIndex;
    private Date mDate;
    private String mNote;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getMoodIndex() {
        return mMoodIndex;
    }

    public void setMoodIndex(int moodIndex) {
        mMoodIndex = moodIndex;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    //TODO delete toString
    @Override
    public String toString() {
        return "History{" +
                "mId=" + mId +
                ", mMoodIndex=" + mMoodIndex +
                ", mDate=" + mDate +
                ", mNote='" + mNote + '\'' +
                '}';
    }
}
