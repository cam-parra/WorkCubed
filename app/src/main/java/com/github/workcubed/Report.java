package com.github.workcubed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;

public class Report extends AppCompatActivity implements CalendarInterface {

    String description;
    BarChart chart;
    boolean enabled = true;
    ArrayList<String> values = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        values.add("2.Q");
        values.add("3.Q");
        values.add("4.Q");
        chart = (BarChart) findViewById(R.id.chart);
        chart.setDrawValueAboveBar(enabled);
        chart.setDrawBarShadow(enabled);
        BarData data = new BarData(values);
        chart.setData(data);
        chart.invalidate();

//        weekly();
    }

    public void daily (View view) {
        description = "Today";
        chart.setDescription(description);
        chart.invalidate();
    }

    public void weekly (View view) {
        description = "Last Week";
        chart.setDescription(description);
        chart.invalidate();
    }

    public void monthly (View view) {
        description = "Last 30 Days";
        chart.setDescription(description);
        chart.invalidate();
    }

    public void quarterly (View view) {
        description = "Last 3 Months";
        chart.setDescription(description);
        chart.invalidate();
    }

}
