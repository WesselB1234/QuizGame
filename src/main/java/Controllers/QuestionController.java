package Controllers;

import Models.QuizGame;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class QuestionController {

    @FXML
    private ToggleGroup radioQuizToggleGroup;
    private QuizGame quizGame;
    private GameController gameController;

    public QuestionController(QuizGame quizGame, GameController gameController) {
        this.quizGame = quizGame;
        this.gameController = gameController;
    }

    @FXML
    protected void onQuestionSubmit() {

        RadioButton selectedButton =  (RadioButton)radioQuizToggleGroup.getSelectedToggle();
        int selectedValue =  Integer.parseInt((String)selectedButton.getUserData());

        System.out.println(selectedValue);
        this.gameController.endQuiz();
    }
}
