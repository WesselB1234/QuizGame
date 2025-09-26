module com.example.projectjavafundamentals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires javafx.base;
    opens Models to com.fasterxml.jackson.databind;


    exports Controllers;
    opens Controllers to javafx.fxml;
    exports AppStartup;
    opens AppStartup to javafx.fxml;
}