package Controllers;

import Models.QuizGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GameController {

    @FXML
    private Label errorLbl;
    @FXML
    private Label quizNameLbl;
    @FXML
    private TextField nameTextField;
    private QuizGame quizGame;

    @FXML
    protected void onGameStart(ActionEvent event) {

    }

    public void setQuizGame(QuizGame quizGame) {
        this.quizGame = quizGame;
        quizNameLbl.setText("Quiz: " + quizGame.title);
    }
}
