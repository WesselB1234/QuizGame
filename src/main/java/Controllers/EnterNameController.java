package Controllers;

import Models.QuizGame;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnterNameController {

    @FXML
    private Label errorLbl;
    @FXML
    private Label quizNameLbl;
    @FXML
    private TextField nameTextField;

    private QuizGame quizGame;
    private GameManager gameManager;
    private GameController gameController;

    public EnterNameController(GameManager gameManager, GameController gameController) {

        this.gameManager = gameManager;
        this.quizGame = this.gameManager.getQuizGame();
        this.gameController = gameController;
        this.gameManager = gameManager;
    }

    private void makeError(String errorMessage) {

        if (!errorLbl.isVisible()){
            errorLbl.setVisible(true);
        }
        errorLbl.setText("An error has occurred: " + errorMessage);
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
            makeError(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        quizNameLbl.setText("Quiz: " + quizGame.title);
    }
}
