package Services;

import Controllers.GameController;
import Factories.QuestionInputsFactory;
import Models.*;
import Models.Context.QuestionViewContext;
import Singletons.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class QuestionService {

    private final QuestionViewContext questionViewContext;

    private final QuizGame quizGame;
    private final Boolean isPracticeMode;

    private final GameManager gameManager;
    private final GameController gameController;
    private final QuestionInputsFactory questionInputsFactory;

    private final IntegerProperty score;

    private Integer currentQuestionIndex;
    private PageElement currentQuestion;
    private Integer currentCountdown;
    private Page currentPage;

    public QuestionService(GameManager gameManager, GameController gameController, QuestionInputsFactory questionInputsFactory, QuestionViewContext questionViewContext) {

        this.questionViewContext = questionViewContext;

        this.gameManager = gameManager;
        quizGame = this.gameManager.getQuizGame();
        isPracticeMode = this.gameManager.getIsPracticeMode();

        this.gameController = gameController;
        this.questionInputsFactory = questionInputsFactory;

        this.currentQuestionIndex = 0;
        this.currentCountdown = 0;

        this.score = new SimpleIntegerProperty(0);
        score.set(0);
        questionViewContext.scoreLbl.textProperty().bind(score.asString("Correct answers: %d"));
    }

    private void setStageSizeBasedOnChildren(){

        Platform.runLater(() -> {
            Scene scene = questionViewContext.errorLbl.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.sizeToScene();
        });
    }

    private void createAnswerInputsByCurrentQuestion(){

        ArrayList<RadioButton> radioButtons = questionInputsFactory.createAnswerInputsListByQuestion(currentQuestion);

        for (RadioButton radioButton : radioButtons) {
            radioButton.setToggleGroup(questionViewContext.radioQuizToggleGroup);
            questionViewContext.questionInputsHolder.getChildren().add(radioButton);
        }
    }

    private void configureQuestionViewObjects(){

        questionViewContext.radioQuizToggleGroup.selectToggle(null);
        questionViewContext.questionInputsHolder.getChildren().clear();
        questionViewContext.questionNameLbl.setText("Question " + (currentQuestionIndex + 1) + ": " + currentQuestion.title);

        String submitQuestionBtnTxt = "Submit question";

        if (currentQuestionIndex + 1 == quizGame.pages.size()) {
            questionViewContext.questionSubmitButton.setText("Finish quiz");
        }
        else if (!questionViewContext.questionSubmitButton.getText().equals(submitQuestionBtnTxt)) {
            questionViewContext.questionSubmitButton.setText(submitQuestionBtnTxt);
        }

        questionViewContext.countdownLbl.setText("Time left: " + Integer.toString(currentCountdown));

        createAnswerInputsByCurrentQuestion();
    }

    public void generateQuestionByQuestionIndex() throws Exception {

        Integer questionIndexAtMethodCall = currentQuestionIndex;

        currentPage = quizGame.pages.get(currentQuestionIndex);
        currentQuestion = currentPage.pageElement;
        currentCountdown = currentPage.timeLimit;

        configureQuestionViewObjects();

        if (isPracticeMode.equals(false)){
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {

                if(currentQuestionIndex.equals(questionIndexAtMethodCall) && currentCountdown <= 0){
                    timeline.stop();
                    try {
                        switchToNewQuestion();
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (!currentQuestionIndex.equals(questionIndexAtMethodCall) || currentCountdown <= 0){
                    timeline.stop();
                }
                else{
                    currentCountdown--;
                    questionViewContext.countdownLbl.setText("Time left: " + Integer.toString(currentCountdown));
                }
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }

        setStageSizeBasedOnChildren();
    }

    public void switchToNewQuestion() throws Exception {

        questionViewContext.errorLbl.setVisible(false);
        currentQuestionIndex++;

        if(currentQuestionIndex >= quizGame.pages.size()) {

            if(isPracticeMode.equals(false)){
                System.out.println("saving");
                gameManager.saveScores();
            }

            gameController.endQuiz();
        }
        else {
            generateQuestionByQuestionIndex();
        }
    }

    private void processCorrectAnswer() throws Exception {
        score.set(score.get() + 1);
        gameManager.setPlayerScore(gameController.getPlayerUserId(), score.get(), currentQuestionIndex + 1);
    }

    private void processIncorrectAnswer() throws Exception {
        gameManager.setPlayerScore(gameController.getPlayerUserId(), score.get(), currentQuestionIndex + 1);
    }

    public void processQuestionAnswer(int selectedValue) throws Exception {

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
}
