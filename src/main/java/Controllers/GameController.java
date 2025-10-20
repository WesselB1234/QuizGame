package Controllers;

import Factories.QuestionViewFactory;
import Services.GameService;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML
    private StackPane layout;
    private GameManager gameManager;
    private String playerUserId;

    private GameService gameService;

    public GameController (GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPlayerUserId(String playerUserId){
        this.playerUserId = playerUserId;
    }

    public String getPlayerUserId(){
        return playerUserId;
    }

    public void endQuiz(){
        gameService.loadScene("/results-view.fxml", new ResultsController(gameManager, this));
    }

    public void startQuiz(){
        gameService.loadScene("/question-view.fxml", new QuestionController(gameManager, this, new QuestionViewFactory()));
    }

    public void startEnterName(){
        gameService.loadScene("/enter-name-view.fxml", new EnterNameController(gameManager, this));
    }

    @FXML
    public void initialize() {

        gameService = new GameService(layout);

        startEnterName();
    }
}
