package org.example.programminexercise_34_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

    // Database URL, username, and password
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/staffdb";
    private static final String USER = "root";
    // @@TODO: Change this
    private static final String PASSWORD = "Utku2022!";

    // Method to obtain database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    // Method to insert staff
    public void insertStaff(String id, String lastName, String firstName, String mi,
                            String address, String city, String state, String telephone, String email) throws SQLException {
        String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);
            statement.setString(2, lastName);
            statement.setString(3, firstName);
            statement.setString(4, mi);
            statement.setString(5, address);
            statement.setString(6, city);
            statement.setString(7, state);
            statement.setString(8, telephone);
            statement.setString(9, email);

            statement.executeUpdate();
        }
    }

    // Method to update staff
    public void updateStaff(String id, String lastName, String firstName, String mi,
                            String address, String city, String state, String telephone, String email) throws SQLException {
        String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, mi);
            statement.setString(4, address);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, telephone);
            statement.setString(8, email);
            statement.setString(9, id);

            statement.executeUpdate();
        }
    }

    // Method to retrieve staff information by ID
    public ResultSet getStaffById(String id) throws SQLException {
        String query = "SELECT * FROM Staff WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);
        return statement.executeQuery();
    }
}
