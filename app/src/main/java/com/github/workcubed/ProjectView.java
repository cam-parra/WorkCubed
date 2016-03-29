package com.github.workcubed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProjectView extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState Saved info from previous states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        final ListView lv = (ListView) findViewById(R.id.listView2);

        List<String> your_array_list = new ArrayList<String>(getAllTasks());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("hello world");
            }
        });

    }

    public static ArrayList<String> getAllTasks () {
        ArrayList<String> taskList = new ArrayList<>();
        int id = 0;
        while (id < 8) {
            String task = "task " + id;
            taskList.add(task);
            id++;
        }

        return taskList;
    }
}
