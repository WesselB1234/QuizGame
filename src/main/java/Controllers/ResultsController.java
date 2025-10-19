package Controllers;

import Models.QuizGame;
import Models.QuizPlayerData;
import Models.QuizPlayerDataManager;
import Singletons.GameManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;

public class ResultsController {

    private QuizGame quizGame;
    private GameManager gameManager;
    private GameController gameController;
    private final String resultsJsonDir = "C:\\Development\\ProjectJavaFundamentals\\src\\main\\JSONs\\QuizResults\\";

    public ResultsController(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.quizGame = this.gameManager.getQuizGame();
        this.gameController = gameController;
    }

    @FXML
    protected void initialize() throws IOException {

        String fileName = resultsJsonDir + quizGame.quizId + "-results.json";

        QuizPlayerDataManager dataManager = gameManager.getQuizPlayerDataManagerFromJson(fileName);
    }
}
