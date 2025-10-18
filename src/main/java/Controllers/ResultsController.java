package Controllers;

import Models.QuizGame;
import javafx.fxml.FXML;

public class ResultsController {

    private QuizGame quizGame;
    private GameController gameController;

    public ResultsController(QuizGame quizGame, GameController gameController) {
        this.quizGame = quizGame;
        this.gameController = gameController;
    }

    @FXML
    protected void initialize()
    {
        System.out.println("results");
    }
}
