module com.example.jdbc_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.jdbc_app to javafx.fxml;
    exports com.example.jdbc_app;
    exports com.example.jdbc_app.controller;
    opens com.example.jdbc_app.controller to javafx.fxml;
    exports com.example.jdbc_app.model;
    opens com.example.jdbc_app.model to javafx.fxml;
}