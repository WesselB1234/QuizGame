package Controllers;

import Models.QuizGame;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

import javafx.scene.Node;
import javafx.fxml.FXML;

public class MenuController {

    private QuizGame getQuizGameFromJson(File file){

        ObjectMapper mapper = new ObjectMapper();
        QuizGame quizGame = null;

        try {
            quizGame = mapper.readValue(file, QuizGame.class);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read JSON from file: " + file.getAbsolutePath());
        }

        return quizGame;
    }

    @FXML
    protected void onFileSelectorClicked(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Quiz JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            System.out.println("Selected file: " + file.getAbsolutePath());
            QuizGame quizGame = getQuizGameFromJson(file);

            if(quizGame != null){
                System.out.println(quizGame.title);
            }
            else {
                System.out.println("No Quiz Game selected");
            }
        }
        else {
            System.out.println("File selection cancelled.");
        }
    }
}
