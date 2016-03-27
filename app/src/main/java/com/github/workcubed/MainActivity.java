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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState Saved date form previous instances
     */
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
            System.out.println("Can't connect! " + se);
//            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, se);

        }

        catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        }
        catch (IllegalAccessException e) {
            System.out.println(e);
        }
        catch (InstantiationException e) {
            System.out.println(e);
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
     * @return                         returns projectList -> the completed list of info for project
     * @throws ClassNotFoundException  Class missing or corrupt
     * @throws SQLException            SQL connection failed
     * @throws InstantiationException  Problem with instantiation
     * @throws IllegalAccessException  Illegal attempt to access
     */
    public static ArrayList<Project> getAllProjects() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

//        Log.i("MainActivity", "Yo Yo Yo");
        String myDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://workedcubed.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com:3306";
        //workedcubed.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com
        String username = "crew";
        String password = "pizza123";
        Class.forName(myDriver).newInstance();
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
        startActivity(intent);

    }

}
