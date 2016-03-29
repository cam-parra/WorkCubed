package com.github.workcubed;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog.OnDateSetListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

public class ProjectForm extends AppCompatActivity {
    private TextView startDateDisplay;
    private TextView endDateDisplay;
    private Button startPickDate;
    private Button endPickDate;
    private Button submit;
    private Calendar startDate;
    private Calendar endDate;
    private EditText projectname;
    private EditText projectDescription;
    private Integer complete;

    static final int DATE_DIALOG_ID = 1;

    private TextView activeDateDisplay;
    private Calendar activeDate;
    Dbhelper newdb;

    /**
     *
     * @param savedInstanceState Saved data form past instances
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_form);

        newdb = new Dbhelper(this);

        projectname = (EditText) findViewById(R.id.editText);
        projectname.getText();

        projectDescription = (EditText) findViewById(R.id.proj_des_edit);
        projectDescription.getText();

        /*  capture our View elements for the start date function   */
        startDateDisplay = (TextView) findViewById(R.id.textView);
        startPickDate = (Button) findViewById(R.id.button2);

        /* get the current date */
        startDate = Calendar.getInstance();

        /* add a click listener to the button   */
        startPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Logger.getLogger("ProjectForm", "MainActivity.onOptionItemSelected() - get ID number" + startDateDisplay);
                showDateDialog(startDateDisplay, startDate);
            }
        });

        /* capture our View elements for the end date function */
        endDateDisplay = (TextView) findViewById(R.id.textView2);
        endPickDate = (Button) findViewById(R.id.button3);

        /* get the current date */
        endDate = Calendar.getInstance();


        /* add a click listener to the button   */
        endPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(endDateDisplay, endDate);
            }
        });

        final CheckBox completedtask  = (CheckBox) findViewById(R.id.checkBox);

        completedtask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                complete = 1;
                Logger.getLogger("HEY THIS WORKED");
            }
        });

        submit = (Button) findViewById(R.id.submit_project);

        final String startdate = new StringBuilder().append(startDate.get(Calendar.MONTH) + 1)
                .append("-")
                .append(startDate.get(Calendar.DAY_OF_MONTH))
                .append("-")
                .append(startDate.get(Calendar.YEAR)).append(" ").toString();

        final String endingdate = new StringBuilder().append(endDate.get(Calendar.MONTH) + 1)
                .append("-")
                .append(endDate.get(Calendar.DAY_OF_MONTH))
                .append("-")
                .append(endDate.get(Calendar.YEAR)).append(" ").toString();

        final String project_name = projectname.getText().toString();
        final String description_text = projectDescription.getText().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newdb.insertProject(project_name, description_text, startdate, endingdate, complete);
                Logger.getLogger("HEY THIS WORKED");
            }
        });





        /* display the current date (this method is below)  */
        updateDisplay(startDateDisplay, startDate);
        updateDisplay(endDateDisplay, endDate);

    }

    /**
     *
     * @param dateDisplay The date ID
     * @param date        The date that will be displayed
     */
    private void updateDisplay(TextView dateDisplay, Calendar date) {
        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(date.get(Calendar.MONTH) + 1).append("-")
                        .append(date.get(Calendar.DAY_OF_MONTH)).append("-")
                        .append(date.get(Calendar.YEAR)).append(" "));

    }

    /**
     *
     * @param dateDisplay The date ID
     * @param date        The date that will be displayed
     */
    public void showDateDialog(TextView dateDisplay, Calendar date) {
        activeDateDisplay = dateDisplay;
        activeDate = date;
        showDialog(DATE_DIALOG_ID);
    }

    private OnDateSetListener dateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            activeDate.set(Calendar.YEAR, year);
            activeDate.set(Calendar.MONTH, monthOfYear);
            activeDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(activeDateDisplay, activeDate);
            unregisterDateDisplay();
        }
    };

    private void unregisterDateDisplay() {
        activeDateDisplay = null;
        activeDate = null;
    }

    /**
     *
     * @param id The projects ID number
     * @return   null
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    /**
     *
     * @param id      The projects ID number
     * @param dialog
     */
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
                break;
        }
    }

    /**
     *
     * @param view ID for the add project button
     */
    public void addProject(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}

