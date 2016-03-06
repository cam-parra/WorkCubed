package com.github.workcubed;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            ListView lv = (ListView) findViewById(R.id.listView);

            List<Project> your_array_list = new ArrayList<Project>(getAllProjects());

            ArrayAdapter<Project> arrayAdapter = new ArrayAdapter<Project>(
                    this,
                    android.R.layout.simple_list_item_1,
                    your_array_list);

            lv.setAdapter(arrayAdapter);
        }

        catch (SQLException se) {
            System.out.println("Can't connect!" + se);
        }

        catch (ClassNotFoundException CNF) {
            System.out.println(CNF);
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

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

    public static ArrayList<Project> getAllProjects() throws ClassNotFoundException, SQLException {

        String myDriver = "org.gjt.mm.mysql.Driver";
        String url = "http://workedcubed.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com";
        String username = "crew";
        String password = "pizza123";
        Class.forName(myDriver);
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            Statement stm;
            stm = connection.createStatement();
            String sql = "Select * From Customer";
            ResultSet rst;
            rst = stm.executeQuery(sql);
            ArrayList<Project> projectList = new ArrayList<>();
            while (rst.next()) {
                Project project = new Project(rst.getInt("ID"), rst.getString("Description"), rst.getTimestamp("DateCreated"),
                        rst.getDate("DateDeadLine"), rst.getInt("Completed"), rst.getString("Name"));
            }
            return projectList;
        }

    }
}
