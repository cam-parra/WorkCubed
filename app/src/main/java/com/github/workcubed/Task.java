package com.github.workcubed;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by cam on 2/24/16.
 */
public class Task {

    public static void add (String name, String description, float hours_actual, float hours_estimated, int project_id, Date deadline_date, boolean status) {

        String url = "workcubeddb.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com:1150";
        String username = "cam";
        String password = "octocat246";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            // the mysql insert statement
            String query = " insert into task (description, hours_actual, hours_estimated, project_id, deadline, status)"
                    + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, description);
            preparedStmt.setFloat(2, hours_actual);
            preparedStmt.setFloat(3, hours_estimated);
            preparedStmt.setInt(4, project_id);
            preparedStmt.setDate(5, deadline_date);
            preparedStmt.setBoolean(6, status);

            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void edit (String name, String description, float hours_actual, float hours_estimated, int project_id, Date deadline_date, boolean status) {
        try
        {
            // create a java mysql database connection
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "workcubeddb.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com:1150";
            String username = "cam";
            String password = "octocat246";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, username, password);

            // create the java mysql update preparedstatement
            String query = "update users set name = ?, description = ?, hours_actual = ?, hours_estimated = ?, project_id = ?, deadline_date = ?, status = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, description);
            preparedStmt.setFloat(3, hours_actual);
            preparedStmt.setFloat(4, hours_estimated);
            preparedStmt.setInt(5, project_id);
            preparedStmt.setDate(6, deadline_date);
            preparedStmt.setBoolean(7, status);
//            preparedStmt.setBoolean(8, id);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void delete () {

    }
}
