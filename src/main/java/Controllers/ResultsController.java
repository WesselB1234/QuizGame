package Controllers;

import Models.QuizGame;
import Models.QuizPlayerData;
import Models.QuizPlayerDataManager;
import Singletons.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultsController {

    @FXML
    private TableView resultsTableView;
    @FXML
    private TableColumn joinDateColumn;

    private QuizGame quizGame;
    private GameManager gameManager;
    private GameController gameController;
    private final String resultsJsonDir = "C:\\Development\\ProjectJavaFundamentals\\src\\main\\JSONs\\QuizResults\\";

    public ResultsController(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.quizGame = this.gameManager.getQuizGame();
        this.gameController = gameController;
    }

    @FXML
    protected void initialize() throws IOException {

        String fileName = resultsJsonDir + quizGame.quizId + "-results.json";

        QuizPlayerDataManager dataManager = gameManager.getQuizPlayerDataManagerFromJson(fileName);
        ObservableList<QuizPlayerData> playerScores = FXCollections.observableArrayList(dataManager.quizPlayersData);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        joinDateColumn.setCellFactory(column -> new TableCell<QuizPlayerData, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                }
                else {
                    setText(item.format(formatter));
                }
            }
        });

        resultsTableView.setItems(playerScores);
    }
}
