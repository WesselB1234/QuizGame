package Controllers;

import Models.QuizGame;
import Singletons.GameManager;
import javafx.fxml.FXML;

public class ResultsController {

    private QuizGame quizGame;
    private GameManager gameManager;
    private GameController gameController;

    public ResultsController(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.quizGame = this.gameManager.getQuizGame();
        this.gameController = gameController;
    }

    @FXML
    protected void initialize() {
        System.out.println("results");
    }
}
