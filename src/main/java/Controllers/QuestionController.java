package Controllers;

import Factories.QuestionViewFactory;
import Models.*;
import Services.ErrorHandlerService;
import Singletons.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Label scoreLbl;
    @FXML
    private Button questionSubmitButton;

    private QuizGame quizGame;
    private GameManager gameManager;
    private GameController gameController;
    private QuestionViewFactory questionViewFactory;

    private Integer currentQuestionIndex;
    private PageElement currentQuestion;
    private Integer currentCountdown;
    private IntegerProperty score;
    private Page currentPage;

    private ErrorHandlerService errorHandlerService;

    public QuestionController(GameManager gameManager, GameController gameController, QuestionViewFactory questionViewFactory) {

        this.gameManager = gameManager;
        this.quizGame = this.gameManager.getQuizGame();
        this.gameController = gameController;
        this.questionViewFactory = questionViewFactory;

        this.currentQuestionIndex = 0;
        this.currentCountdown = 0;
        this.score = new SimpleIntegerProperty(0);
        score.set(0);
    }

    private void generateQuestionByQuestionIndex(){

        Integer questionIndexAtMethodCall = currentQuestionIndex;

        currentPage = quizGame.pages.get(currentQuestionIndex);
        currentQuestion = currentPage.pageElement;
        questionViewFactory.createNewQuestionView(currentQuestion, radioQuizToggleGroup, questionInputsHolder, questionNameLbl, currentQuestionIndex,
               currentQuestionIndex + 1 == quizGame.pages.size(), questionSubmitButton);

        currentCountdown = currentPage.timeLimit;
        countdownLbl.setText(Integer.toString(currentCountdown));

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {

            if(currentQuestionIndex.equals(questionIndexAtMethodCall) && currentCountdown <= 0){
                timeline.stop();
                switchToNewQuestion();
            }
            else if (!currentQuestionIndex.equals(questionIndexAtMethodCall) || currentCountdown <= 0){
                timeline.stop();
            }
            else{
                currentCountdown--;
                countdownLbl.setText(Integer.toString(currentCountdown));
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void switchToNewQuestion(){

        try{
            errorLbl.setVisible(false);
            currentQuestionIndex++;

            if(currentQuestionIndex >= quizGame.pages.size()) {
                gameManager.saveScores();
                gameController.endQuiz();
            }
            else {
                generateQuestionByQuestionIndex();
            }
        }
        catch (Exception e){
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }

    private void processCorrectAnswer() throws Exception {
        score.set(score.get() + 1);
        gameManager.setPlayerScore(gameController.getPlayerUserId(), score.get(), currentQuestionIndex + 1);
    }

    private void processIncorrectAnswer() throws Exception {
        gameManager.setPlayerScore(gameController.getPlayerUserId(), score.get(), currentQuestionIndex + 1);
    }

    private void processQuestionAnswer(int selectedValue) throws Exception {

        if (currentQuestion instanceof BooleanElement) {

            boolean correctAnswer = ((BooleanElement) currentQuestion).correctAnswer;

            if ((correctAnswer && selectedValue == 1) || (!correctAnswer && selectedValue == 0)) {
                processCorrectAnswer();
            }
            else {
                processIncorrectAnswer();
            }
        }
        else if (currentQuestion instanceof RadioGroupElement) {

            int correctAnswer = ((RadioGroupElement) currentQuestion).correctAnswer;

            if (correctAnswer == selectedValue) {
                processCorrectAnswer();
            }
            else {
                processIncorrectAnswer();
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
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }

    @FXML
    public void initialize() {

        errorHandlerService = new ErrorHandlerService(errorLbl);
        scoreLbl.textProperty().bind(score.asString("Score: %d"));

        generateQuestionByQuestionIndex();
    }
}
