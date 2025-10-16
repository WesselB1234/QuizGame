package Controllers;

import Factories.QuestionViewFactory;
import Models.PageElement;
import Models.QuizGame;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuestionController {

    @FXML
    private ToggleGroup radioQuizToggleGroup;
    @FXML
    private VBox questionInputsHolder;
    @FXML
    private Label questionNameLabel;

    private QuizGame quizGame;
    private GameController gameController;
    private Integer currentQuestionIndex;
    private PageElement currentQuestion;
    private QuestionViewFactory questionViewFactory;

    public QuestionController(QuizGame quizGame, GameController gameController, QuestionViewFactory questionViewFactory) {
        this.quizGame = quizGame;
        this.gameController = gameController;
        this.questionViewFactory = questionViewFactory;
        this.currentQuestionIndex = 0;
    }

    private void generateQuestionByQuestionIndex(){

        currentQuestion = quizGame.pages.get(currentQuestionIndex).pageElement;
        questionViewFactory.createNewQuestionView(currentQuestion, radioQuizToggleGroup, questionInputsHolder, questionNameLabel, currentQuestionIndex);
    }

    private void switchToNewQuestion(){

        System.out.println("Switching to New Question");
        currentQuestionIndex++;

        if(currentQuestionIndex >= quizGame.pages.size()) {
            gameController.endQuiz();
        }
        else {
            generateQuestionByQuestionIndex();
        }
    }

    @FXML
    protected void onQuestionSubmit() {

        RadioButton selectedButton =  (RadioButton)radioQuizToggleGroup.getSelectedToggle();
        int selectedValue =  Integer.parseInt((String)selectedButton.getUserData());

        System.out.println("Selected value is " + selectedValue);
        switchToNewQuestion();
    }

    @FXML
    public void initialize() {
        generateQuestionByQuestionIndex();
    }
}
