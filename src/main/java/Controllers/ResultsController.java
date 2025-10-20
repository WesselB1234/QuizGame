package Controllers;

import Models.QuizGame;
import Models.QuizPlayerData;
import Models.QuizPlayerDataManager;
import Services.ErrorHandlerService;
import Singletons.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    @FXML
    private Label errorLbl;

    private GameManager gameManager;
    private ErrorHandlerService errorHandlerService;

    public ResultsController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @FXML
    protected void initialize() {

        errorHandlerService = new ErrorHandlerService(errorLbl);

        try{
            QuizPlayerDataManager dataManager = gameManager.getQuizPlayerDataManagerFromJson();
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
        catch (Exception e){
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }
}
