package Controllers;

import Models.QuizGame;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

import javafx.scene.Node;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private Label selectedQuizbl;
    @FXML
    private Label errorQuizSelectorLbl;
    @FXML
    private Button quizStarterBtn;
    private QuizGame quizGame;

    private QuizGame getQuizGameFromJson(File file){

        ObjectMapper mapper = new ObjectMapper();
        QuizGame quizGame = null;

        try {
            quizGame = mapper.readValue(file, QuizGame.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return quizGame;
    }


    @FXML
    protected void onFileSelectorClicked(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open quiz JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            QuizGame quizGame = getQuizGameFromJson(file);

            if(quizGame != null){

                this.quizGame = quizGame;

                selectedQuizbl.setText("Selected quiz: " + quizGame.title);
                errorQuizSelectorLbl.setVisible(false);
                quizStarterBtn.setVisible(true);
            }
            else {
                selectedQuizbl.setText("No quiz selected");
                errorQuizSelectorLbl.setText("Something went wrong selecting the quiz json file");
                errorQuizSelectorLbl.setVisible(true);
                quizStarterBtn.setVisible(false);
            }
        }
    }

    @FXML
    protected void onQuizStart(ActionEvent event) {
        System.out.println(quizGame.title);
    }
}
