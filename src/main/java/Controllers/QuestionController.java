package Controllers;

import Models.PageElement;
import Models.QuizGame;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuestionController {

    @FXML
    private ToggleGroup radioQuizToggleGroup;
    @FXML
    private VBox questionInputsHolder;
    private QuizGame quizGame;
    private GameController gameController;
    private Integer currentQuestionIndex;
    private PageElement currentQuestion;

    public QuestionController(QuizGame quizGame, GameController gameController) {
        this.quizGame = quizGame;
        this.gameController = gameController;
        this.currentQuestionIndex = 0;
    }

    private void generateQuestion(PageElement pageElement) {

    }

    private void switchToNewQuestion(){

        questionInputsHolder.getChildren().clear();

        System.out.println("Switching to New Question");
        currentQuestionIndex++;

        for (int i = 0; i < 4; i++){
            RadioButton radioButton = new RadioButton();
            radioButton.setText("Test " + i);
            radioButton.setUserData(String.valueOf(i));
            radioButton.setToggleGroup(radioQuizToggleGroup);
            questionInputsHolder.getChildren().add(radioButton);
        }
    }

    @FXML
    protected void onQuestionSubmit() {

        RadioButton selectedButton =  (RadioButton)radioQuizToggleGroup.getSelectedToggle();
        int selectedValue =  Integer.parseInt((String)selectedButton.getUserData());

        System.out.println("Selected value is " + selectedValue);
        //switchToNewQuestion();
    }

    @FXML
    public void initialize() {
        switchToNewQuestion();
    }
}
