package jullien.matthieu.moodtracker.Model;

import jullien.matthieu.moodtracker.R;

//  General informations about the 5 moods
public class MoodInfo {
    public static final int NB_MOOD = 5;
    public static final int HAPPY_INDEX = 3;

    public static final int LAYOUTS[] = {
            R.layout.fragment_mood_sad,
            R.layout.fragment_mood_disappointed,
            R.layout.fragment_mood_normal,
            R.layout.fragment_mood_happy,
            R.layout.fragment_mood_super_happy
    };

    public static final int COLORS[] = {
            R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow,
            R.color.light_sage
    };
}
