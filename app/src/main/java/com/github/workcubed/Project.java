package com.github.workcubed;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Dave on 2/24/16.
 */



public class Project {

    String myDriver = "org.gjt.mm.mysql.Driver";
    String url = "http://workedcubed.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com";
    String username = "crew";
    String password = "pizza123";

    int _id;
    String _name;
    String _description;
    int _completed;
    Timestamp _date_created;
    Date _deadline_date;


    public Project (int ID, String description, Timestamp date_created, Date date_deadline, int completed, String name) {
        _id = ID;
        _description = description;
        _date_created = date_created;
        _deadline_date = date_deadline;
        _completed = completed;
        _name = name;
    }

    public void add_project (String name, String description, Date deadline_date, int status) {



        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            // the mysql insert statement
            String query = " insert into Project (Description, DateDeadline, Completed, Name)"
                    + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, description);
            preparedStmt.setDate(2, deadline_date);
            preparedStmt.setInt(3, status);
            preparedStmt.setString(4, name);

            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

    }

    public void edit_project (String name, String description, Date date_created, Date deadline_date, int status) {
        try
        {
            // create a java mysql database connection
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(url, username, password);

            // create the java mysql update preparedstatement
            String query = "update Project set Name = ?, Description = ?, DateDeadline = ?, Completed = ? where ID = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, description);
            preparedStmt.setDate(2, deadline_date);
            preparedStmt.setInt(3, status);
            preparedStmt.setString(4, name);
//            preparedStmt.setInt(5, id);

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

    public void delete_project (int id) {
        try
        {
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(url, username, password);

            // create the mysql delete statement.
            // i'm deleting the row where the id is "3", which corresponds to my
            // "Barney Rubble" record.
            String query = "delete from Project where ID = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            // execute the preparedstatement
            preparedStmt.execute();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
