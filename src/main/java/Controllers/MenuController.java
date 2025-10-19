package Controllers;

import Models.QuizGame;
import Singletons.GameManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javafx.scene.Node;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private Label selectedQuizLbl;
    @FXML
    private Label errorLbl;
    @FXML
    private Button quizStarterBtn;
    private QuizGame quizGame;
    private GameManager gameManager;

    private QuizGame getQuizGameFromJson(File file){

        ObjectMapper mapper = new ObjectMapper();
        QuizGame quizGame = null;

        try {
            quizGame = mapper.readValue(file, QuizGame.class);
            quizGame.quizId = generateQuizId();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return quizGame;
    }

    private String generateQuizId(){

        Random rand = new Random();
        StringBuilder quizId = new StringBuilder();

        for(int i = 0; i < 5; i++){
            quizId.append(Integer.toString(rand.nextInt(10)));
        }

        Path path = Paths.get("C:\\Development\\ProjectJavaFundamentals\\src\\main\\JSONs\\QuizResults\\" + quizId + "_results.json");

        if (Files.exists(path)) {
            return generateQuizId();
        }

        return quizId.toString();
    }

    @FXML
    protected void onFileSelectorClicked(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open quiz JSON File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        File file = new File("C:\\Development\\ProjectJavaFundamentals\\src\\main\\JSONs\\QuizJson1.JSON");//fileChooser.showOpenDialog(stage);

        if (file != null) {

            QuizGame quizGame = getQuizGameFromJson(file);

            if(quizGame != null){

                this.quizGame = quizGame;
                gameManager = new GameManager(quizGame);

                selectedQuizLbl.setText("Selected quiz: " + quizGame.title);
                errorLbl.setVisible(false);
                quizStarterBtn.setVisible(true);
            }
            else {
                selectedQuizLbl.setText("No quiz selected");
                errorLbl.setText("Something went wrong selecting the quiz json file");
                errorLbl.setVisible(true);
                quizStarterBtn.setVisible(false);
            }
        }
    }

    @FXML
    protected void onQuizStart(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-view.fxml"));
        GameController gameController = new GameController(gameManager);
        loader.setController(gameController);

        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Game window");
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
