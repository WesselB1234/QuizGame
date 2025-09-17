module com.example.projectjavafundamentals {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectjavafundamentals to javafx.fxml;
    exports com.example.projectjavafundamentals;
}