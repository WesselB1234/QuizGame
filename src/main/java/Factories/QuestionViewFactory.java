package Factories;

import Models.PageElement;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuestionViewFactory {

    public void createNewAnswerInput(String text, String value,ToggleGroup radioQuizToggleGroup, VBox questionInputsHolder){

        RadioButton radioButton = new RadioButton();
        radioButton.setText(text);
        radioButton.setUserData(String.valueOf(value));
        radioButton.setToggleGroup(radioQuizToggleGroup);
        questionInputsHolder.getChildren().add(radioButton);
    }

    public void createNewQuestionView(ToggleGroup radioQuizToggleGroup, VBox questionInputsHolder){

        questionInputsHolder.getChildren().clear();

        for (int i = 0; i < 4; i++){
            createNewAnswerInput("test " + i, String.valueOf(i), radioQuizToggleGroup, questionInputsHolder);
        }
    }
}
