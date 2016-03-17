package com.github.workcubed;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Created by Dave on 2/24/16.
 */
public class Task {
    String myDriver = "org.gjt.mm.mysql.Driver";
    String url = "http://workedcubed.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com";
    String username = "crew";
    String password = "pizza123";

    /**
     *
     * @param name             Name of the project
     * @param description      Basic information on the project
     * @param hours_actual     The actual time it took to complete project
     * @param hours_estimated  The estimated time taken to complete project
     * @param project_id       The projects ID number
     * @param deadline_date    The date the project needs to be done by
     * @param status           The current complete status of the project
     */
    public void add (String name, String description, Time hours_actual, Time hours_estimated, int project_id, Date deadline_date, int status) {


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");

            // the mysql insert statement
            String query = " insert into Task (Description, Hours_Actual, Hours_Expected, Complete, Deadline, Project_ID, Name)"
                    + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, description);
            preparedStmt.setTime(2, hours_actual);
            preparedStmt.setTime(3, hours_estimated);
            preparedStmt.setInt(4, status);
            preparedStmt.setDate(5, deadline_date);
            preparedStmt.setInt(6, project_id);
            preparedStmt.setString(7, name);



            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     *
     * @param name             Name of the project
     * @param description      Basic information on the project
     * @param hours_actual     The actual time it took to complete project
     * @param hours_estimated  The estimated time taken to complete project
     * @param project_id       The projects ID number
     * @param deadline_date    The date the project needs to be done by
     * @param status           The current complete status of the project
     */
    public void edit (String name, String description, Time hours_actual, Time hours_estimated, int project_id, Date deadline_date, boolean status) {
        try
        {
            // create a java mysql database connection


            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(url, username, password);

            // create the java mysql update preparedstatement
            String query = "update Task set Name = ?, Description = ?, Hours_Actual = ?, Hours_Expected = ?, Project_ID = ?, Deadline = ?, Complete = ? where ID = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, description);
            preparedStmt.setTime(3, hours_actual);
            preparedStmt.setTime(4, hours_estimated);
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

    /**
     *
     * @param id ID number of soon to be deleted project
     */
    public void delete (int id) {
        try
        {
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(url, username, password);

            // create the mysql delete statement.
            // i'm deleting the row where the id is "3", which corresponds to my
            // "Barney Rubble" record.
            String query = "delete from Task where ID = ?";
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
