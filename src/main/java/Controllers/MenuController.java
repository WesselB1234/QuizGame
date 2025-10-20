package Controllers;

import Models.QuizGame;
import Services.ErrorHandlerService;
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
    private final String resultsFolderDir = "C:\\Development\\ProjectJavaFundamentals\\src\\main\\JSONs\\QuizResults\\";

    private ErrorHandlerService errorHandlerService;

    private QuizGame getQuizGameFromJson(File file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        quizGame = mapper.readValue(file, QuizGame.class);
        quizGame.quizId = generateQuizId();

        return quizGame;
    }

    private String generateQuizId(){

        Random rand = new Random();
        StringBuilder quizId = new StringBuilder();

        for(int i = 0; i < 5; i++){
            quizId.append(Integer.toString(rand.nextInt(10)));
        }

        Path path = Paths.get(resultsFolderDir + quizId + "-results.json");

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

        try{
            this.quizGame = getQuizGameFromJson(file);;
            gameManager = new GameManager(quizGame, resultsFolderDir);

            selectedQuizLbl.setText("Selected quiz: " + quizGame.title);
            errorLbl.setVisible(false);
            quizStarterBtn.setVisible(true);
        }
        catch(Exception e){
            selectedQuizLbl.setText("No quiz selected.");
            quizStarterBtn.setVisible(false);
            errorHandlerService.displayErrorMessage(e.getMessage());
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
        newStage.setWidth(900);
        newStage.setHeight(500);
        newStage.show();
    }

    @FXML
    protected void initialize() {
        errorHandlerService = new ErrorHandlerService(errorLbl);
    }
}
