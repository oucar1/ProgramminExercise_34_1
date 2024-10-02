package org.example.programminexercise_34_1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffController extends Application {

    // JavaFX TextFields, Buttons, and Message Label
    private TextField idField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField firstNameField = new TextField();
    private TextField miField = new TextField();
    private TextField addressField = new TextField();
    private TextField cityField = new TextField();
    private TextField stateField = new TextField();
    private TextField telephoneField = new TextField();
    private TextField emailField = new TextField();
    private Label messageLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        // Setting up a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10)); // Padding around the grid
        gridPane.setHgap(10); // Horizontal gap between columns
        gridPane.setVgap(10); // Vertical gap between rows
        gridPane.setAlignment(Pos.CENTER); // Center the grid on the screen

        // Labels and Input Fields
        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(idField, 1, 0);

        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(lastNameField, 1, 1);

        gridPane.add(new Label("First Name:"), 2, 1);
        gridPane.add(firstNameField, 3, 1);

        gridPane.add(new Label("MI:"), 4, 1);
        gridPane.add(miField, 5, 1);

        gridPane.add(new Label("Address:"), 0, 2);
        gridPane.add(addressField, 1, 2, 5, 1);

        gridPane.add(new Label("City:"), 0, 3);
        gridPane.add(cityField, 1, 3);

        gridPane.add(new Label("State:"), 2, 3);
        gridPane.add(stateField, 3, 3);

        gridPane.add(new Label("Telephone:"), 0, 4);
        gridPane.add(telephoneField, 1, 4, 5, 1);

        gridPane.add(new Label("Email:"), 0, 5);
        gridPane.add(emailField, 1, 5, 5, 1);

        // Buttons
        Button viewButton = new Button("View");
        Button insertButton = new Button("Insert");
        Button updateButton = new Button("Update");
        Button clearButton = new Button("Clear");

        // Setting button width
        insertButton.setMinWidth(75);
        updateButton.setMinWidth(75);
        clearButton.setMinWidth(75);
        viewButton.setMinWidth(75);

        // Adding buttons to an HBox for better alignment
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(viewButton, insertButton, updateButton, clearButton);

        // Adding the buttonBox and message label to the gridPane
        gridPane.add(buttonBox, 1, 6, 5, 1);
        gridPane.add(messageLabel, 1, 7, 5, 1);

        // Setting up the Scene (Window)
        Scene scene = new Scene(gridPane, 700, 350);
        primaryStage.setTitle("Staff Management");
        primaryStage.setScene(scene);
        primaryStage.show(); // Display the window

        // Event Handlers for Buttons

        // View Button Action
        viewButton.setOnAction(e -> {
            String id = idField.getText();
            if (id == null || id.trim().isEmpty() || id.length() != 4) {
                showMessage("Please enter a valid 4-character ID");
                return;
            }

            DatabaseHandler dbHandler = new DatabaseHandler();
            try (ResultSet resultSet = dbHandler.getStaffById(id)) {
                if (resultSet != null && resultSet.next()) {
                    lastNameField.setText(resultSet.getString("lastName"));
                    firstNameField.setText(resultSet.getString("firstName"));
                    miField.setText(resultSet.getString("mi"));
                    addressField.setText(resultSet.getString("address"));
                    cityField.setText(resultSet.getString("city"));
                    stateField.setText(resultSet.getString("state"));
                    telephoneField.setText(resultSet.getString("telephone"));
                    emailField.setText(resultSet.getString("email"));
                    showMessage("Record found.");
                } else {
                    showMessage("Record not found");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showMessage("Error occurred while retrieving data");
            }
        });

        // Insert Button Action
        insertButton.setOnAction(e -> {
            if (!validateInputs()) {
                return;
            }

            DatabaseHandler dbHandler = new DatabaseHandler();
            try {
                dbHandler.insertStaff(
                        idField.getText(),
                        lastNameField.getText(),
                        firstNameField.getText(),
                        miField.getText(),
                        addressField.getText(),
                        cityField.getText(),
                        stateField.getText(),
                        telephoneField.getText(),
                        emailField.getText()
                );
                showMessage("Record inserted successfully");
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    showMessage("Error: ID already exists. Please use a unique ID.");
                } else {
                    showMessage("Error occurred while inserting data: " + ex.getMessage());
                }
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
                showMessage("Unexpected error occurred while inserting data");
            }
        });

        // Update Button Action
        updateButton.setOnAction(e -> {
            if (!validateInputs()) {
                return;
            }

            DatabaseHandler dbHandler = new DatabaseHandler();
            try {
                dbHandler.updateStaff(
                        idField.getText(),
                        lastNameField.getText(),
                        firstNameField.getText(),
                        miField.getText(),
                        addressField.getText(),
                        cityField.getText(),
                        stateField.getText(),
                        telephoneField.getText(),
                        emailField.getText()
                );
                showMessage("Record updated successfully");
            } catch (Exception ex) {
                ex.printStackTrace();
                showMessage("Error occurred while updating data");
            }
        });

        // Clear Button Action
        clearButton.setOnAction(e -> clearFields());
    }

    // Method to display a message in the application
    private void showMessage(String message) {
        messageLabel.setText(message);
    }

    // Method to clear all input fields
    private void clearFields() {
        idField.clear();
        lastNameField.clear();
        firstNameField.clear();
        miField.clear();
        addressField.clear();
        cityField.clear();
        stateField.clear();
        telephoneField.clear();
        emailField.clear();
        showMessage("");
    }

    // Method to validate input fields
    private boolean validateInputs() {
        if (idField.getText().length() != 4) {
            showMessage("ID must be 4 characters.");
            return false;
        }
        if (lastNameField.getText().length() > 15) {
            showMessage("Last Name must be 15 characters or less.");
            return false;
        }
        if (firstNameField.getText().length() > 15) {
            showMessage("First Name must be 15 characters or less.");
            return false;
        }
        if (miField.getText().length() > 1) {
            showMessage("MI must be 1 character.");
            return false;
        }
        if (addressField.getText().length() > 20) {
            showMessage("Address must be 20 characters or less.");
            return false;
        }
        if (cityField.getText().length() > 20) {
            showMessage("City must be 20 characters or less.");
            return false;
        }
        if (stateField.getText().length() != 2) {
            showMessage("State must be 2 characters.");
            return false;
        }
        if (telephoneField.getText().length() != 10 || !telephoneField.getText().matches("\\d+")) {
            showMessage("Telephone must be 10 digits.");
            return false;
        }
        if (emailField.getText().length() > 40) {
            showMessage("Email must be 40 characters or less.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
