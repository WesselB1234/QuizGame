package Services;

import Models.QuizPlayerData;
import Models.QuizPlayerDataManager;
import Singletons.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ResultsService {

    private GameManager gameManager;
    private TableView<QuizPlayerData> resultsTableView;

    private ErrorHandlerService errorHandlerService;

    public ResultsService(GameManager gameManager, TableView<QuizPlayerData> resultsTableView, ErrorHandlerService errorHandlerService) {
        this.gameManager = gameManager;
        this.resultsTableView = resultsTableView;
        this.errorHandlerService = errorHandlerService;
    }

    public void getAndPutScoresInTable(){

        try{
            QuizPlayerDataManager dataManager = gameManager.getQuizPlayerDataManagerFromJson();
            ObservableList<QuizPlayerData> playerScores = FXCollections.observableArrayList(dataManager.quizPlayersData);

            resultsTableView.setItems(playerScores);
        }
        catch (Exception e){
            errorHandlerService.displayErrorMessage(e.getMessage());
        }
    }
}
