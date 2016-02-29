package com.github.workcubed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by cam on 2/24/16.
 */



public class Project {

    int year;

    public int add_project () {

        return 0;

    }

    public void edit_project () {

    }

    public void delete_project () {

    }

    public void add_task () {

    }

    public void edit_task () {

    }

    public void delete_task () {

    }

    public void connect () {
        String url = "jdbc:mysql://localhost:3306/javabase";
        String username = "java";
        String password = "password";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

}
