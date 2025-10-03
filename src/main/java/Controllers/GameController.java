package Controllers;

import Models.QuizGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class GameController {

    @FXML
    private VBox nameSelectorPage;
    @FXML
    private VBox questionsPage;
    @FXML
    private Label errorLbl;
    @FXML
    private Label quizNameLbl;
    @FXML
    private TextField nameTextField;
    @FXML
    private ToggleGroup radioQuizToggleGroup;
    private QuizGame quizGame;

    public void setQuizGame(QuizGame quizGame) {
        this.quizGame = quizGame;
        quizNameLbl.setText("Quiz: " + quizGame.title);
    }

    private void startQuiz(){
        nameSelectorPage.setVisible(false);
        questionsPage.setVisible(true);
    }

    @FXML
    protected void onGameStart(ActionEvent event) {

        if(nameTextField.getText().isEmpty()) {
            errorLbl.setText("Please enter your name.");
            errorLbl.setVisible(true);
        }
        else if(quizGame != null){
            startQuiz();
        }
    }

    @FXML
    protected void onQuestionSubmit() {
        RadioButton selectedButton =  (RadioButton)radioQuizToggleGroup.getSelectedToggle();
        int selectedValue =  Integer.parseInt((String)selectedButton.getUserData());

        System.out.println(selectedValue);
    }
}
