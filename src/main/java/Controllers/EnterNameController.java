package Controllers;

import Models.QuizGame;
import Services.ErrorHandlerService;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnterNameController {

    @FXML
    private Label errorLbl;
    @FXML
    private TextField nameTextField;

    private final QuizGame quizGame;
    private GameManager gameManager;
    private final GameController gameController;

    private ErrorHandlerService errorHandlerService;

    public EnterNameController(GameManager gameManager, GameController gameController) {

        this.gameManager = gameManager;
        this.quizGame = this.gameManager.getQuizGame();
        this.gameController = gameController;
        this.gameManager = gameManager;
    }

    @FXML
    protected void onGameStart() {

        try{
            String name = nameTextField.getText();

            if (name.isEmpty()) {
                throw new Exception("Please enter your name.");
            }

            String playerUserId = gameManager.addPlayerToQuizPlayers(name, quizGame.pages.size());
            gameController.setPlayerUserId(playerUserId);
            gameController.startQuiz();
        }
        catch (Exception e){
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        errorHandlerService = new ErrorHandlerService(errorLbl);
    }
}
