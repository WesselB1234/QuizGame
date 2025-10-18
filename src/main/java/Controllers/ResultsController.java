package Controllers;

import Models.QuizGame;
import Singletons.GameManager;
import javafx.fxml.FXML;

public class ResultsController {

    private QuizGame quizGame;
    private GameManager gameManager;
    private GameController gameController;

    public ResultsController(QuizGame quizGame, GameManager gameManager, GameController gameController) {
        this.quizGame = quizGame;
        this.gameManager = gameManager;
        this.gameController = gameController;
    }

    @FXML
    protected void initialize() {
        System.out.println("results");
    }
}
