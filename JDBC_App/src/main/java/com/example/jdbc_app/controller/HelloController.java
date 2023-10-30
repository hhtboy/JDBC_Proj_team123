package com.example.jdbc_app.controller;

import com.example.jdbc_app.model.MysqlConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    private final MysqlConnector mysqlConnector;

    public HelloController() {
        mysqlConnector = new MysqlConnector();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        mysqlConnector.connect();
    }
}