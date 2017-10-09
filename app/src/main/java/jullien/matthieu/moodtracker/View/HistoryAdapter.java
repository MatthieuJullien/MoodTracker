package jullien.matthieu.moodtracker.View;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import jullien.matthieu.moodtracker.Model.History;
import jullien.matthieu.moodtracker.Model.MoodInfo;
import jullien.matthieu.moodtracker.R;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class HistoryAdapter extends ArrayAdapter<History> {
    private DisplayMetrics mMetrics;

    public HistoryAdapter(Context context, ArrayList<History> historyList, DisplayMetrics metrics) {
        super(context, 0, historyList);
        mMetrics = metrics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        History history = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_day, parent, false);
        }
        int moodIndex = history.getMoodIndex();

        convertView.getLayoutParams().width = mMetrics.widthPixels / 5 * (1 + moodIndex);
        convertView.setBackgroundResource(MoodInfo.COLORS[moodIndex]);

        TextView historyDayText = convertView.findViewById(R.id.history_day_text);
        ImageView historyDayImg = convertView.findViewById(R.id.history_day_image);

        long nbDayPassed = DAYS.convert(new Date().getTime() - history.getDate().getTime(), MILLISECONDS);
        String strdayPassed;
        switch ((int)nbDayPassed) {
            case 0:
                strdayPassed = "Hier";
                break;
            case 1:
                strdayPassed = "Avant hier";
                break;
            case 2:
                strdayPassed = "Il y a trois jours";
                break;
            case 3:
                strdayPassed = "Il y a quatre jours";
                break;
            case 4:
                strdayPassed = "Il y a cinq jours";
                break;
            case 5:
                strdayPassed = "Il y a six jours";
                break;
            case 6:
                strdayPassed = "Il y a une semaine";
                break;
            default:
                strdayPassed = "Il y a " + nbDayPassed + "jours";
                break;
        }
        historyDayText.setText(strdayPassed);
        final String note = history.getNote();
        if (note != null && !note.equals("")) {
            historyDayImg.setVisibility(View.VISIBLE);
            historyDayImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), note, Toast.LENGTH_SHORT).show();
                }
            });
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
