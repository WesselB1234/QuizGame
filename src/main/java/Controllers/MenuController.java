package Controllers;

import Models.QuizGame;
import Services.ErrorHandlerService;
import Services.MenuService;
import Singletons.GameManager;
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
    private MenuService menuService;

    public MenuController(){
        menuService = new MenuService(resultsFolderDir);
    }

    @FXML
    protected void onFileSelectorClicked(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open quiz JSON File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        //File file = new File("C:\\Development\\ProjectJavaFundamentals\\src\\main\\JSONs\\QuizJson1.JSON");
        try{
            this.quizGame = menuService.getQuizGameFromJson(file);;
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
