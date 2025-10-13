package Controllers;

import Models.QuizGame;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Label selectedQuizLbl;
    @FXML
    private Label errorLbl;
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

                selectedQuizLbl.setText("Selected quiz: " + quizGame.title);
                errorLbl.setVisible(false);
                quizStarterBtn.setVisible(true);
            }
            else {
                selectedQuizLbl.setText("No quiz selected");
                errorLbl.setText("Something went wrong selecting the quiz json file");
                errorLbl.setVisible(true);
                quizStarterBtn.setVisible(false);
            }
        }
    }

    @FXML
    protected void onQuizStart(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-view.fxml"));
        GameController gameController = new GameController(quizGame);
        loader.setController(gameController);

        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Game window");
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
