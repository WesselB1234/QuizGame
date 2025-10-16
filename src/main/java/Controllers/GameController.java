package Controllers;

import Factories.QuestionViewFactory;
import Models.QuizGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameController {

    @FXML
    private Pane layout;
    private QuizGame quizGame;

    public GameController (QuizGame quizGame) {
        this.quizGame = quizGame;
    }

    public void loadScene(String name, Object controller) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());

            if (!layout.getChildren().isEmpty())
            {
                layout.getChildren().removeFirst();
            }

            layout.getChildren().add(scene.getRoot());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endQuiz(){
        loadScene("/results-view.fxml", new ResultsController(quizGame, this));
    }

    public void startQuiz(){
        loadScene("/question-view.fxml", new QuestionController(quizGame, this, new QuestionViewFactory()));
    }

    public void startEnterName(){
        loadScene("/enter-name-view.fxml", new EnterNameController(quizGame, this));
    }

    @FXML
    public void initialize() {
        //startEnterName();
        startQuiz();
    }
}
