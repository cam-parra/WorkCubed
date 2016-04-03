package com.github.workcubed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class TaskView extends AppCompatActivity {

    Dbhelper db;
    String task_name;
    String desc;
    Integer status;
    Integer hours_estimated;
    Integer hours_actual;
    Button update;
    String project_name;
    Integer id;
    public final static String EXTRA_MESSAGE = "project name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        db = new Dbhelper(this);

        Intent intent = getIntent();
        task_name = intent.getStringExtra(ProjectView.TASK_MESSAGE);
        desc = db.getTaskDescByName(task_name);
        status = db.getTaskStatusByName(task_name);
        hours_estimated = db.getTaskHoursEstimatedByName(task_name);
        hours_actual = db.getTaskHoursActualByName(task_name);
        project_name = db.getTaskColumnProjectname(task_name);
        id = db.getTaskIDByName(task_name);
        System.out.println(task_name + " " + desc + " " + status + " " + hours_estimated + " " + hours_actual);

        final TextView name_text = (TextView) findViewById(R.id.task_name_text);
        name_text.setText(task_name);

        final TextView desc_text = (TextView) findViewById(R.id.task_desc_text);
        desc_text.setText(desc);

        final EditText hours_est = (EditText) findViewById(R.id.est_hours);
        hours_est.setText(Integer.toString(hours_estimated));

        final EditText hours_act = (EditText) findViewById(R.id.act_hours);
        hours_act.setText(Integer.toString(hours_actual));

        final CheckBox status_box = (CheckBox) findViewById(R.id.completed_box);

        if(status < 1) {
            status_box.setChecked(false);
        }
        else {
            status_box.setChecked(true);
        }

        update = (Button) findViewById(R.id.update_task);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userName = name_text.getText().toString();
                String userDesc = desc_text.getText().toString();
                String userEstString = hours_est.getText().toString();
                Float userEst= Float.parseFloat(userEstString);
                String userActString = hours_act.getText().toString();
                Float userAct = Float.parseFloat(userActString);

                if (status_box.isChecked())
                    status = 1;
                else
                    status = 0;

                db.updateTask(id, userName, userDesc, userAct, userEst, status, project_name);
                projectView(v);
            }
        });

    }

    public void projectView (View view) {
        Intent intent = new Intent(this, ProjectView.class);
        intent.putExtra(EXTRA_MESSAGE, project_name);
        startActivity(intent);
    }
}
