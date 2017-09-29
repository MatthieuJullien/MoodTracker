package jullien.matthieu.moodtracker.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        HistoryDbHelper mDbHelper = new HistoryDbHelper(this);
        ArrayList<History> historyList = mDbHelper.getHistory();
        HistoryAdapter historyAdapter = new HistoryAdapter(this, historyList, metrics);
        ListView listView = (ListView)findViewById(R.id.history_listview);
        listView.setAdapter(historyAdapter);
    }
}
