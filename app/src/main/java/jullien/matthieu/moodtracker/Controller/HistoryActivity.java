package jullien.matthieu.moodtracker.Controller;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import jullien.matthieu.moodtracker.Model.History;
import jullien.matthieu.moodtracker.R;
import jullien.matthieu.moodtracker.View.HistoryAdapter;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);//doesn't work ?! TODO masquer barre de titre

        setContentView(R.layout.activity_history);

        HistoryDbHelper mDbHelper = new HistoryDbHelper(this);
        final ArrayList<History> historyList = mDbHelper.getHistory();

        final float[] moodDistribution = getMoodDistribution(historyList);

        ImageView history_chart = (ImageView)findViewById(R.id.history_piechart_image);
        history_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chartActivity = new Intent(HistoryActivity.this, ChartActivity.class);
                chartActivity.putExtra("data", moodDistribution);
                startActivity(chartActivity);
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        HistoryAdapter historyAdapter = new HistoryAdapter(this, historyList, metrics);
        ListView listView = (ListView)findViewById(R.id.history_listview);
        listView.setAdapter(historyAdapter);
    }

    private float[] getMoodDistribution(ArrayList<History> historyList) {
        float[] moodDistribution = {0, 0, 0, 0, 0};
        for (History h : historyList) {
            moodDistribution[h.getMoodIndex()]++;
        }
        return moodDistribution;
    }
}
