package Factories;

import Models.BooleanElement;
import Models.Context.QuestionViewContext;
import Models.PageElement;
import Models.RadioGroupElement;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuestionViewFactory {

    public void createNewAnswerInput(String text, String value, ToggleGroup radioQuizToggleGroup, VBox questionInputsHolder) {

        RadioButton radioButton = new RadioButton();
        radioButton.setText(text);
        radioButton.setUserData(String.valueOf(value));
        radioButton.setToggleGroup(radioQuizToggleGroup);
        questionInputsHolder.getChildren().add(radioButton);
    }

    public void createNewQuestionView(PageElement pageElement, Integer questionIndex, Boolean isLastQuestion, QuestionViewContext questionViewContext) {

        questionViewContext.radioQuizToggleGroup.selectToggle(null);
        questionViewContext.questionInputsHolder.getChildren().clear();
        questionViewContext.questionNameLbl.setText("Question " + (questionIndex + 1) + ": " + pageElement.title);

        if (pageElement instanceof BooleanElement) {
            createNewAnswerInput("True", "1", questionViewContext.radioQuizToggleGroup, questionViewContext.questionInputsHolder);
            createNewAnswerInput("False", "0", questionViewContext.radioQuizToggleGroup, questionViewContext.questionInputsHolder);
        }
        else if (pageElement instanceof RadioGroupElement) {

            String[] choices = ((RadioGroupElement) pageElement).choices;

            for (int i = 0; i < choices.length; i++) {
                createNewAnswerInput(choices[i], String.valueOf(i), questionViewContext.radioQuizToggleGroup, questionViewContext.questionInputsHolder);
            }
        }

        String submitQuestionBtnTxt = "Submit question";

        if (isLastQuestion) {
            questionViewContext.questionSubmitButton.setText("Finish quiz");
        }
        else if (!questionViewContext.questionSubmitButton.getText().equals(submitQuestionBtnTxt)) {
            questionViewContext.questionSubmitButton.setText(submitQuestionBtnTxt);
        }
    }
}