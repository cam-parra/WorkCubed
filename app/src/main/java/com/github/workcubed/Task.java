package com.github.workcubed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by cam on 2/24/16.
 */
public class Task {
    int start_day;
    int start_month;
    int start_year;
    int end_day;
    int end_month;
    int end_year;
    String description;
    float time_to_complete_in_hours;
    float time_spent_in_hours;
    float reminder_time;

    public static int add () {
        return 0;
    }

    public void edit () {

    }

    public void delete () {

    }

    public static boolean connect () {

        String url = "jdbc:mysql://localhost:3306/javabase";
        String username = "java";
        String password = "password";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        return false;
    }

}
