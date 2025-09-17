package com.example.projectjavafundamentals;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to the java fundamentals final project that will be made by Wessel!!!");
    }
}
