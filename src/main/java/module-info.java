module com.example.projectjavafundamentals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports Controllers;
    opens Controllers to javafx.fxml;
    exports AppStartup;
    opens AppStartup to javafx.fxml;
}