package com.github.workcubed;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Dave on 2/24/16.
 */



public class Project {

    public void add_project (String name, String description, Date date_created, Date deadline_date, boolean status) {

        String url = "workcubeddb.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com:1150";
        String username = "cam";
        String password = "octocat246";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            // the mysql insert statement
            String query = " insert into project (name, description, date_created, deadline_date, status)"
                    + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString (2, description);
            preparedStmt.setDate(3, date_created);
            preparedStmt.setDate(4, deadline_date);
            preparedStmt.setBoolean(5, status);

            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

    }

    public void edit_project (String name, String description, Date date_created, Date deadline_date, boolean status) {
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
            String query = "update users set name = ?, description = ?, date_created = ?, deadline_date = ?, status = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, description);
            preparedStmt.setDate(3, date_created);
            preparedStmt.setDate(4, deadline_date);
            preparedStmt.setBoolean(5, status);
//            preparedStmt.setInt(6, id);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Could not connect to database!");
            System.err.println(e.getMessage());
        }

    }

    public void delete_project () {

    }
}
