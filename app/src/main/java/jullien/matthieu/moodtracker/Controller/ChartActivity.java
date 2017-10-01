package jullien.matthieu.moodtracker.Controller;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import jullien.matthieu.moodtracker.Model.MoodInfo;
import jullien.matthieu.moodtracker.R;

// Uses the MPAndroidChart library
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
        dataSet.setValueTextSize(14f);

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

        // Clear the description label
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setData(pieData);

        // Refresh
        pieChart.invalidate();
    }
}
