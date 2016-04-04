package com.github.workcubed;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;


import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity implements CalendarInterface {

    String description;
    BarChart chart;
    boolean enabled = true;
    ArrayList<String> values = new ArrayList<String>();
    private Typeface mTf;
    private Dbhelper dbhelper;
    float average;

    List<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        dbhelper = new Dbhelper(this);
        tasks = new ArrayList<>(dbhelper.getAllCompletedTasks());

        chart = (BarChart) findViewById(R.id.chart);

//        values.add("2.Q");
//        values.add("3.Q");
//        values.add("4.Q");
//        chart = (BarChart) findViewById(R.id.chart);
        chart.setDrawValueAboveBar(false);
        chart.setDrawBarShadow(false);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

//        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

//        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

//        setData(12, 50);

//        BarData data = new BarData(values);
//        chart.setData(data);
        chart.invalidate();

//        weekly();
    }

    public void daily (View view) {
        dbhelper = new Dbhelper(this);
        Integer actual = 0;
        Integer estimated = 0;

        description = "Today";
        chart.setMaxVisibleValueCount(30);
        chart.setDescription(description);

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yValsGood = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsBad = new ArrayList<BarEntry>();
        System.out.println(tasks.size());
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                xVals.add(tasks.get(i));

                actual = dbhelper.getTaskHoursActualByName(tasks.get(i));
                estimated = dbhelper.getTaskHoursEstimatedByName(tasks.get(i));

                float result = (actual * 100) / estimated;
                average += result;
                if (result > 100)
                    yValsBad.add(new BarEntry(result, i));
                else
                    yValsGood.add(new BarEntry(result, i));

            }
        }
        average = average / tasks.size();
        TextView average_textView = (TextView) findViewById(R.id.average);
        String float_average = (String.format("%.2f", average) + " %");

//        String average_string = (Float.toString(average) + " %");
        average_textView.setText(float_average);

        BarDataSet bad_productivity = new BarDataSet(yValsBad, "bad");
        bad_productivity.setColors(new int[]{R.color.colorPrimary});
        BarDataSet good_productivity = new BarDataSet(yValsGood, "good");
        good_productivity.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(good_productivity);
        dataSets.add(bad_productivity);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        chart.setData(data);

        chart.invalidate();
    }

    public void weekly (View view) {
        dbhelper = new Dbhelper(this);
        Integer actual = 0;
        Integer estimated = 0;

        description = "Last Week";
        chart.setMaxVisibleValueCount(30);
        chart.setDescription(description);

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yValsGood = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsBad = new ArrayList<BarEntry>();
        System.out.println(tasks.size());
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                xVals.add(tasks.get(i));

                actual = dbhelper.getTaskHoursActualByName(tasks.get(i));
                estimated = dbhelper.getTaskHoursEstimatedByName(tasks.get(i));

                float result = (actual * 100) / estimated;

                if (result > 100)
                    yValsBad.add(new BarEntry(result, i));
                else
                    yValsGood.add(new BarEntry(result, i));

            }
        }
        BarDataSet bad_productivity = new BarDataSet(yValsBad, "bad");
        bad_productivity.setColors(new int[]{R.color.colorPrimary});
        BarDataSet good_productivity = new BarDataSet(yValsGood, "good");
        good_productivity.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(good_productivity);
        dataSets.add(bad_productivity);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        chart.setData(data);

        chart.invalidate();
    }

    public void monthly (View view) {
        dbhelper = new Dbhelper(this);
        Integer actual = 0;
        Integer estimated = 0;

        description = "Last 30 Days";
        chart.setMaxVisibleValueCount(30);
        chart.setDescription(description);

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yValsGood = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsBad = new ArrayList<BarEntry>();
        System.out.println(tasks.size());
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                xVals.add(tasks.get(i));

                actual = dbhelper.getTaskHoursActualByName(tasks.get(i));
                estimated = dbhelper.getTaskHoursEstimatedByName(tasks.get(i));

                float result = (actual * 100) / estimated;

                if (result > 100)
                    yValsBad.add(new BarEntry(result, i));
                else
                    yValsGood.add(new BarEntry(result, i));

            }
        }
        BarDataSet bad_productivity = new BarDataSet(yValsBad, "bad");
        bad_productivity.setColors(new int[]{R.color.colorPrimary});
        BarDataSet good_productivity = new BarDataSet(yValsGood, "good");
        good_productivity.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(good_productivity);
        dataSets.add(bad_productivity);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        chart.setData(data);

        chart.invalidate();
    }

    public void quarterly (View view) {
        dbhelper = new Dbhelper(this);
        Integer actual = 0;
        Integer estimated = 0;

        description = "Last 3 Months";
        chart.setMaxVisibleValueCount(30);
        chart.setDescription(description);

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yValsGood = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsBad = new ArrayList<BarEntry>();
        System.out.println(tasks.size());
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                xVals.add(tasks.get(i));

                actual = dbhelper.getTaskHoursActualByName(tasks.get(i));
                estimated = dbhelper.getTaskHoursEstimatedByName(tasks.get(i));

                float result = (actual * 100) / estimated;

                if (result > 100)
                    yValsBad.add(new BarEntry(result, i));
                else
                    yValsGood.add(new BarEntry(result, i));

            }
        }
        BarDataSet bad_productivity = new BarDataSet(yValsBad, "bad");
        bad_productivity.setColors(new int[]{R.color.colorPrimary});
        BarDataSet good_productivity = new BarDataSet(yValsGood, "good");
        good_productivity.setBarSpacePercent(35f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(good_productivity);
        dataSets.add(bad_productivity);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        chart.setData(data);

        chart.invalidate();
    }

    public void homeButton (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
