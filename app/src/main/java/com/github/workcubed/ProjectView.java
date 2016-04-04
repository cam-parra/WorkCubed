package com.github.workcubed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectView extends AppCompatActivity {

    Dbhelper db;
    String project_name;
    String desc;
    String deadline;
    String id;
    String task_name;
    public final static String EXTRA_MESSAGE = "project name";
    public final static String TASK_MESSAGE = "task name";

    /**
     *
     * @param savedInstanceState Saved info from previous states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        db = new Dbhelper(this);

        Intent intent = getIntent();
        project_name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        desc = db.getProjectDescByName(project_name);
        deadline = db.getProjectDeadlineByName(project_name);
        id = db.getProjectIDByName(project_name);

        TextView name_text = (TextView) findViewById(R.id.project_name);
        name_text.setText(project_name);

        TextView desc_text = (TextView) findViewById(R.id.project_description);
        desc_text.setText(desc);

        TextView deadline_text = (TextView) findViewById(R.id.deadline);
        deadline_text.setText(deadline);

        final ListView lv = (ListView) findViewById(R.id.listView2);

        List<String> your_array_list = new ArrayList<String>(db.getAllTasks(project_name));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("hello world");
                task_name = (String) parent.getItemAtPosition(position);

                System.out.println(project_name);

                taskDisplay(view);
            }
        });

    }

    public static ArrayList<String> getAllTasks () {
        ArrayList<String> taskList = new ArrayList<>();


        return taskList;
    }

    public void goToTaskForm (View view) {
        Intent intent = new Intent(this,TaskForm.class);
        intent.putExtra(EXTRA_MESSAGE, project_name);
        startActivity(intent);
    }

    public void taskDisplay  (View view) {
        Intent intent = new Intent(this,TaskView.class);
        intent.putExtra(TASK_MESSAGE, task_name);
        startActivity(intent);
    }

    public void homeButton (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
