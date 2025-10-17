package Factories;

import Models.BooleanElement;
import Models.PageElement;
import Models.RadioGroupElement;
import javafx.scene.control.Label;
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

    public void createNewQuestionView(PageElement pageElement, ToggleGroup radioQuizToggleGroup, VBox questionInputsHolder, Label questionNameLbl, Integer questionIndex) {

        radioQuizToggleGroup.selectToggle(null);
        questionInputsHolder.getChildren().clear();
        questionNameLbl.setText("Question " + (questionIndex + 1) + ": " + pageElement.title);

        if (pageElement instanceof BooleanElement) {
            createNewAnswerInput("True", "1", radioQuizToggleGroup, questionInputsHolder);
            createNewAnswerInput("False", "0", radioQuizToggleGroup, questionInputsHolder);
        } else if (pageElement instanceof RadioGroupElement) {

            String[] choices = ((RadioGroupElement) pageElement).choices;

            for (int i = 0; i < choices.length; i++) {
                createNewAnswerInput(choices[i], String.valueOf(i), radioQuizToggleGroup, questionInputsHolder);
            }
        }
    }
}