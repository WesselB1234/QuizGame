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

    public ResultsService(GameManager gameManager, TableView<QuizPlayerData> resultsTableView) {
        this.gameManager = gameManager;
        this.resultsTableView = resultsTableView;
    }

    public void getAndPutScoresInTable() throws Exception{

        QuizPlayerDataManager dataManager = gameManager.getQuizPlayerDataManagerFromJson();
        ObservableList<QuizPlayerData> playerScores = FXCollections.observableArrayList(dataManager.quizPlayersData);

        resultsTableView.setItems(playerScores);
    }
}
