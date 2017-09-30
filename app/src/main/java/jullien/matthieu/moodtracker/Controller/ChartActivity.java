package jullien.matthieu.moodtracker.Controller;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import jullien.matthieu.moodtracker.Model.History;
import jullien.matthieu.moodtracker.Model.MoodInfo;
import jullien.matthieu.moodtracker.R;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        PieChart pieChart = (PieChart)findViewById(R.id.chart);
        pieChart.setUsePercentValues(true);


        float[] moodDistribution;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                moodDistribution = null;
            } else {
                moodDistribution = extras.getFloatArray("data");
            }
        } else {
            moodDistribution  = (float[]) savedInstanceState.getSerializable("data");
        }

        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entries.add(new PieEntry(moodDistribution[i], i));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Repartition des humeurs de ces sept derniers jours");
        dataSet.setValueTextSize(10f);

        dataSet.setColors(new int[] {
                ResourcesCompat.getColor(getResources(), MoodInfo.COLORS[0], null),
                ResourcesCompat.getColor(getResources(), MoodInfo.COLORS[1], null),
                ResourcesCompat.getColor(getResources(), MoodInfo.COLORS[2], null),
                ResourcesCompat.getColor(getResources(), MoodInfo.COLORS[3], null),
                ResourcesCompat.getColor(getResources(), MoodInfo.COLORS[4], null)
        });
        // Space between slices (in degrees)
        dataSet.setSliceSpace(3f);

        PieData pieData = new PieData(dataSet);
        Description description = new Description();
        description.setText("");
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setDescription(description);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}
