package Controllers;

import Controllers.Interfaces.IScoresUploadNotifier;
import Models.QuizPlayerData;
import Services.ErrorHandlerService;
import Services.ResultsService;
import Singletons.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultsController implements IScoresUploadNotifier {

    @FXML
    private TableView<QuizPlayerData> resultsTableView;
    @FXML
    private TableColumn<QuizPlayerData, LocalDateTime> joinDateColumn;
    @FXML
    private TableColumn<QuizPlayerData, Integer> scorePercentageColumn;
    @FXML
    private Label localPlayerScoreLbl;
    @FXML
    private Label perfectScoreLbl;
    @FXML
    private Label errorLbl;

    private final GameManager gameManager;
    private ErrorHandlerService errorHandlerService;
    private ResultsService resultsService;

    public ResultsController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @FXML
    protected void initialize() {

        try{
            gameManager.subscribeNotifierToScoresObserver(this);
            errorHandlerService = new ErrorHandlerService(errorLbl);
            resultsService = new ResultsService(gameManager, resultsTableView);

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

            scorePercentageColumn.setCellFactory(column -> new TableCell<QuizPlayerData, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {

                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    }
                    else {
                        setText(item + "%");
                    }
                }
            });

            resultsService.getAndPutScoresInTable();
        }
        catch (Exception e) {
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }

    @Override
    public void onNotifyScoreUpload() {

        try {
            resultsService.getAndPutScoresInTable();
        }
        catch (Exception e) {
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void onCsvExport(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Folder to Save CSV");
            
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            File selectedDirectory = directoryChooser.showDialog(stage);
            String filePath = selectedDirectory.getAbsolutePath();

            gameManager.exportResultsToCsv(filePath);
        }
        catch (Exception e) {
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }
}
