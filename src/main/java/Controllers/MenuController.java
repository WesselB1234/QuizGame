package Controllers;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.Node;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    protected void onFileSelectorClicked(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            System.out.println("Selected file: " + file.getAbsolutePath());
        } else {
            System.out.println("File selection cancelled.");
        }
    }
}
