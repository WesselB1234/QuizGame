package Controllers;

import Factories.QuestionViewFactory;
import Models.QuizGame;
import Services.GameService;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameController {

    @FXML
    private VBox layout;
    @FXML
    private Label quizNameLbl;
    @FXML
    private Label quizDescriptionLbl;

    private final GameManager gameManager;
    private String playerUserId;

    private GameService gameService;
    private final QuizGame quizGame;

    public GameController (GameManager gameManager) {
        this.gameManager = gameManager;
        quizGame = gameManager.getQuizGame();
    }

    public void setPlayerUserId(String playerUserId){
        this.playerUserId = playerUserId;
    }

    public String getPlayerUserId(){
        return playerUserId;
    }

    public void endQuiz(){
        gameService.loadScene("/results-view.fxml", new ResultsController(gameManager));
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

        quizNameLbl.setText("Quiz: " + quizGame.title);
        quizDescriptionLbl.setText("Description: " + quizGame.description);

        startEnterName();
    }
}
