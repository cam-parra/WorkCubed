package com.github.workcubed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    Dbhelper db;
    String project_name;
    public final static String EXTRA_MESSAGE = "project name";
    /**
     *
     * @param savedInstanceState Saved date form previous instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Dbhelper(this);
        final ListView lv = (ListView) findViewById(R.id.listView);

        List<String> your_array_list = new ArrayList<String>(db.getAllProjects());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, your_array_list);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                project_name = (String) parent.getItemAtPosition(position);

                System.out.println(project_name);

                projectDisplay(view);
            }
        });

    }

    /**
     *
     * @param menu Information to put into the menu
     * @return     Returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    /**
     *
     * @param item  Information to put into the action bar
     * @return      If id is valid, returns true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param view The button ID
     */
    public void addProject(View view) {

        Intent intent = new Intent(this, ProjectForm.class);
        startActivity(intent);
    }

    /**
     *
     * @param view The button ID
     */
    public void projectDisplay(View view) {
        Intent intent = new Intent(this,ProjectView.class);
        intent.putExtra(EXTRA_MESSAGE, project_name);
        startActivity(intent);

    }

    public void reportOnClick (View view) {
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

}
