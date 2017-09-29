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

import jullien.matthieu.moodtracker.Model.History;
import jullien.matthieu.moodtracker.Model.MoodInfo;
import jullien.matthieu.moodtracker.R;

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


        TextView historyDayText = convertView.findViewById(R.id.history_day_text);
        ImageView historyDayImg = convertView.findViewById(R.id.history_day_image);
        convertView.setBackgroundResource(MoodInfo.COLORS[moodIndex]);




        historyDayText.setText("Il y a X days");
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
