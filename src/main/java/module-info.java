module com.example.projectjavafundamentals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires javafx.base;
    requires javafx.graphics;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens Models to com.fasterxml.jackson.databind, javafx.base;

    exports Controllers;
    opens Controllers to javafx.fxml;
    exports AppStartup;
    opens AppStartup to javafx.fxml;
}