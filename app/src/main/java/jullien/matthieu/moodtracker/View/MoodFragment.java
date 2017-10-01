package jullien.matthieu.moodtracker.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jullien.matthieu.moodtracker.Model.MoodInfo;

// This class represent a mood on screen.
public class MoodFragment extends Fragment {
    private int mMoodIndex;

    // newInstance constructor for creating fragment with arguments
    public static MoodFragment newInstance(int moodIndex) {
        MoodFragment newFragment = new MoodFragment();
        Bundle args = new Bundle();
        args.putInt("moodIndex", moodIndex);
        newFragment.setArguments(args);
        return newFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoodIndex = getArguments().getInt("moodIndex", 0);
    }

    // Return the View associated with the current mood.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = MoodInfo.LAYOUTS[mMoodIndex];
        return inflater.inflate(layoutId, container, false);
    }
}
