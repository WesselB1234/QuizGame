package Controllers;

import Models.QuizGame;

public class ResultsController {

    private QuizGame quizGame;
    private GameController gameController;

    public ResultsController(QuizGame quizGame, GameController gameController) {
        this.quizGame = quizGame;
        this.gameController = gameController;
    }
}
