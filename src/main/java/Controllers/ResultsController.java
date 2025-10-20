package Controllers;

import Controllers.Interfaces.IScoresUploadNotifier;
import Models.QuizPlayerData;
import Services.ErrorHandlerService;
import Services.ResultsService;
import Singletons.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultsController implements IScoresUploadNotifier {

    @FXML
    private TableView resultsTableView;
    @FXML
    private TableColumn joinDateColumn;
    @FXML
    private Label errorLbl;

    private GameManager gameManager;
    private ErrorHandlerService errorHandlerService;
    private ResultsService resultsService;

    public ResultsController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @FXML
    protected void initialize() {

        gameManager.subscribeNotifierToScoresObserver(this);
        errorHandlerService = new ErrorHandlerService(errorLbl);
        resultsService = new ResultsService(gameManager, resultsTableView, errorHandlerService);

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

        resultsService.getAndPutScoresInTable();
    }

    @Override
    public void onNotifyScoreUpload() {
        resultsService.getAndPutScoresInTable();
    }
}
