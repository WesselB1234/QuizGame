package Controllers;

import Factories.QuestionViewFactory;
import Models.BooleanElement;
import Models.PageElement;
import Models.QuizGame;
import Models.RadioGroupElement;
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
    @FXML
    private Label errorLabel;

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

        errorLabel.setVisible(false);
        currentQuestionIndex++;

        if(currentQuestionIndex >= quizGame.pages.size()) {
            gameController.endQuiz();
        }
        else {
            generateQuestionByQuestionIndex();
        }
    }

    private void makeError(String errorMessage){

        if (!errorLabel.isVisible()){
            errorLabel.setVisible(true);
        }
        errorLabel.setText("An error has occurred: " + errorMessage);
    }

    @FXML
    protected void onQuestionSubmit() {

        try{
            RadioButton selectedButton =  (RadioButton)radioQuizToggleGroup.getSelectedToggle();

            if (selectedButton == null){
                throw new Exception("Please select an answer.");
            }

            int selectedValue = Integer.parseInt((String)selectedButton.getUserData());

            if (currentQuestion instanceof BooleanElement) {

                boolean correctAnswer = ((BooleanElement) currentQuestion).correctAnswer;

                if ((correctAnswer && selectedValue == 1) || (!correctAnswer && selectedValue == 0)) {
                    System.out.println("Congrats");
                }
            }
            else if (currentQuestion instanceof RadioGroupElement) {

                int correctAnswer = ((RadioGroupElement) currentQuestion).correctAnswer;

                if (correctAnswer == selectedValue) {
                    System.out.println("Congrats");
                }
            }

            switchToNewQuestion();
        }
        catch (Exception e){
            makeError(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        generateQuestionByQuestionIndex();
    }
}
