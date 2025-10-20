package Controllers;

import Factories.QuestionViewFactory;
import Models.*;
import Models.Context.QuestionViewContext;
import Services.ErrorHandlerService;
import Services.QuestionService;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Label questionNameLbl;
    @FXML
    private Label errorLbl;
    @FXML
    private Label countdownLbl;
    @FXML
    private Label scoreLbl;
    @FXML
    private Button questionSubmitButton;

    private GameManager gameManager;
    private GameController gameController;
    private QuestionViewFactory questionViewFactory;

    private ErrorHandlerService errorHandlerService;
    private QuestionService questionService;

    public QuestionController(GameManager gameManager, GameController gameController, QuestionViewFactory questionViewFactory) {
        this.gameManager = gameManager;
        this.gameController = gameController;
        this.questionViewFactory = questionViewFactory;
    }

    @FXML
    protected void onQuestionSubmit() {

        try{
            RadioButton selectedButton =  (RadioButton)radioQuizToggleGroup.getSelectedToggle();

            if (selectedButton == null){
                throw new Exception("Please select an answer.");
            }

            int selectedValue = Integer.parseInt((String)selectedButton.getUserData());

            questionService.processQuestionAnswer(selectedValue);
            questionService.switchToNewQuestion();
        }
        catch (Exception e){
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }

    @FXML
    public void initialize() {

        errorHandlerService = new ErrorHandlerService(errorLbl);
        QuestionViewContext questionViewContext = new QuestionViewContext(
                radioQuizToggleGroup,
                questionInputsHolder,
                questionNameLbl,
                errorLbl,
                countdownLbl,
                scoreLbl,
                questionSubmitButton
        );

        questionService = new QuestionService(gameManager, gameController, questionViewFactory, questionViewContext, errorHandlerService);
        questionService.generateQuestionByQuestionIndex();
    }
}
