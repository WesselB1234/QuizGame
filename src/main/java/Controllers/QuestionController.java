package Controllers;

import Factories.QuestionViewFactory;
import Models.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class QuestionController {

    @FXML
    private ToggleGroup radioQuizToggleGroup;
    @FXML
    private VBox questionInputsHolder;
    @FXML
    private Label questionNameLbl;
    @FXML
    private Label errorLbl;
    @FXML
    private Label countdownLbl;

    private QuizGame quizGame;
    private GameController gameController;
    private Integer currentQuestionIndex;
    private PageElement currentQuestion;
    private Page currentPage;
    private Integer currentCountdown;
    private QuestionViewFactory questionViewFactory;

    public QuestionController(QuizGame quizGame, GameController gameController, QuestionViewFactory questionViewFactory) {
        this.quizGame = quizGame;
        this.gameController = gameController;
        this.questionViewFactory = questionViewFactory;
        this.currentQuestionIndex = 0;
        this.currentCountdown = 0;
    }

    private void generateQuestionByQuestionIndex(){

        Integer questionIndexAtMethodCall = currentQuestionIndex;

        currentPage = quizGame.pages.get(currentQuestionIndex);
        currentQuestion = currentPage.pageElement;
        questionViewFactory.createNewQuestionView(currentQuestion, radioQuizToggleGroup, questionInputsHolder, questionNameLbl, currentQuestionIndex);

        this.currentCountdown = currentPage.timeLimit;
        countdownLbl.setText(Integer.toString(this.currentCountdown));

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {

            if(currentQuestionIndex.equals(questionIndexAtMethodCall) && this.currentCountdown <= 0){
                timeline.stop();
                switchToNewQuestion();
            }
            else if (!currentQuestionIndex.equals(questionIndexAtMethodCall) || this.currentCountdown <= 0){
                timeline.stop();
            }
            else{
                this.currentCountdown--;
                countdownLbl.setText(Integer.toString(this.currentCountdown));
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void switchToNewQuestion(){

        //errorLbl.setVisible(false);
        currentQuestionIndex++;

        if(currentQuestionIndex >= quizGame.pages.size()) {
            gameController.endQuiz();
        }
        else {
            generateQuestionByQuestionIndex();
        }
    }

    private void makeError(String errorMessage){

        if (!errorLbl.isVisible()){
            errorLbl.setVisible(true);
        }
        errorLbl.setText("An error has occurred: " + errorMessage);
    }

    private void processQuestionAnswer(int selectedValue){

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
    }

    @FXML
    protected void onQuestionSubmit() {

        try{
            RadioButton selectedButton =  (RadioButton)radioQuizToggleGroup.getSelectedToggle();

            if (selectedButton == null){
                throw new Exception("Please select an answer.");
            }

            int selectedValue = Integer.parseInt((String)selectedButton.getUserData());

            processQuestionAnswer(selectedValue);
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
