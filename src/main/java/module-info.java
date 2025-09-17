module com.example.projectjavafundamentals2_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectjavafundamentals2_1 to javafx.fxml;
    exports com.example.projectjavafundamentals2_1;
}