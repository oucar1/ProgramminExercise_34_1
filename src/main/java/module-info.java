module org.example.programminexercise_34_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens org.example.programminexercise_34_1 to javafx.fxml;
    exports org.example.programminexercise_34_1;
}