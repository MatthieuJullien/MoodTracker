package jullien.matthieu.moodtracker.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jullien.matthieu.moodtracker.R;

/**
 * This class represent a mood on screen.
 */
public class MoodFragment extends Fragment /*implements View.OnClickListener*/{
    private int mMoodIndex;

    //newInstance constructor for creating fragment with arguments
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
        int layoutId;
        switch (mMoodIndex) {
            case 0:
                layoutId = R.layout.fragment_mood_sad;
                break;
            case 1:
                layoutId = R.layout.fragment_mood_disappointed;
                break;
            case 2:
                layoutId = R.layout.fragment_mood_normal;
                break;
            case 3:
                layoutId = R.layout.fragment_mood_happy;
                break;
            case 4:
                layoutId = R.layout.fragment_mood_super_happy;
                break;
            default:
                layoutId = R.layout.fragment_mood_happy;
                break;
        }
        return inflater.inflate(layoutId, container, false);
    }
}
