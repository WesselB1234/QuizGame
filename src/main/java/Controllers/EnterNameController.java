package Controllers;

import Models.QuizGame;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnterNameController {

    @FXML
    private Label errorLbl;
    @FXML
    private Label quizNameLbl;
    @FXML
    private TextField nameTextField;
    @FXML
    private QuizGame quizGame;
    private GameManager gameManager;
    private GameController gameController;

    public EnterNameController(QuizGame quizGame, GameManager gameManager, GameController gameController) {
        this.quizGame = quizGame;
        this.gameController = gameController;
        this.gameManager = gameManager;
    }

    @FXML
    protected void onGameStart() {

        if (nameTextField.getText().isEmpty()) {
            errorLbl.setText("Please enter your name.");
            errorLbl.setVisible(true);
        }
        else {
            gameController.startQuiz();
        }
    }

    @FXML
    public void initialize() {
        quizNameLbl.setText("Quiz: " + quizGame.title);
    }
}
