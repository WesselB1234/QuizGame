package Factories;

import Models.BooleanElement;
import Models.PageElement;
import Models.RadioGroupElement;
import javafx.scene.control.RadioButton;

import java.util.ArrayList;

public class QuestionInputsFactory {

    private RadioButton createAnswerInput(String text, String value) {

        RadioButton radioButton = new RadioButton();
        radioButton.setText(text);
        radioButton.setUserData(String.valueOf(value));

        return radioButton;
    }

    public ArrayList<RadioButton> createAnswerInputsListByQuestion(PageElement question) {

        ArrayList<RadioButton> radioButtons = new ArrayList<>();

        if (question instanceof BooleanElement) {
            radioButtons.add(createAnswerInput("True", "1"));
            radioButtons.add(createAnswerInput("False", "0"));
        }
        else if (question instanceof RadioGroupElement) {

            String[] choices = ((RadioGroupElement) question).choices;

            for (int i = 0; i < choices.length; i++) {
                radioButtons.add(createAnswerInput(choices[i], String.valueOf(i)));
            }
        }

        return radioButtons;
    }
}