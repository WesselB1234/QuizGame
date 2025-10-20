package Models.Context;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuestionViewContext {

    public ToggleGroup radioQuizToggleGroup;
    public VBox questionInputsHolder;
    public Label questionNameLbl;
    public Label errorLbl;
    public Label countdownLbl;
    public Label scoreLbl;
    public Button questionSubmitButton;

    public QuestionViewContext(ToggleGroup radioQuizToggleGroup, VBox questionInputsHolder, Label questionNameLbl,  Label errorLbl, Label countdownLbl, Label scoreLbl, Button questionSubmitButton) {

        this.radioQuizToggleGroup = radioQuizToggleGroup;
        this.questionInputsHolder = questionInputsHolder;
        this.questionNameLbl = questionNameLbl;
        this.errorLbl = errorLbl;
        this.countdownLbl = countdownLbl;
        this.scoreLbl = scoreLbl;
        this.questionSubmitButton = questionSubmitButton;
    }
}
