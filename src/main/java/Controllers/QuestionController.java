package Controllers;

import Factories.QuestionInputsFactory;
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

    private final GameManager gameManager;
    private final GameController gameController;
    private final QuestionInputsFactory questionInputsFactory;

    private ErrorHandlerService errorHandlerService;
    private QuestionService questionService;

    public QuestionController(GameManager gameManager, GameController gameController, QuestionInputsFactory questionInputsFactory) {
        this.gameManager = gameManager;
        this.gameController = gameController;
        this.questionInputsFactory = questionInputsFactory;
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

        try {
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

            countdownLbl.setVisible(!gameManager.getIsPracticeMode());

            questionService = new QuestionService(gameManager, gameController, questionInputsFactory, questionViewContext);
            questionService.generateQuestionByQuestionIndex();
        }
        catch (Exception e) {
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }
}
