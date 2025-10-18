package Controllers;

import Factories.QuestionViewFactory;
import Models.QuizGame;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameController {

    @FXML
    private Pane layout;
    private QuizGame quizGame;
    private GameManager gameManager;
    private String playerUserId;

    public GameController (QuizGame quizGame, GameManager gameManager) {
        this.quizGame = quizGame;
        this.gameManager = gameManager;
    }

    public void loadScene(String name, Object controller) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            fxmlLoader.setController(controller);

            Parent newContent = fxmlLoader.load();

            layout.getChildren().clear();
            layout.getChildren().add(newContent);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlayerUserId(String playerUserId){
        this.playerUserId = playerUserId;
    }

    public String getPlayerUserId(){
        return playerUserId;
    }

    public void endQuiz(){
        loadScene("/results-view.fxml", new ResultsController(quizGame, gameManager, this));
    }

    public void startQuiz(){
        loadScene("/question-view.fxml", new QuestionController(quizGame, gameManager, this, new QuestionViewFactory()));
    }

    public void startEnterName(){
        loadScene("/enter-name-view.fxml", new EnterNameController(quizGame, gameManager, this));
    }

    @FXML
    public void initialize() {
        startEnterName();
    }
}
