package Services;

import Models.QuizPlayerData;
import Models.QuizPlayerDataManager;
import Singletons.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

public class ResultsService {

    private final GameManager gameManager;
    private final TableView<QuizPlayerData> resultsTableView;

    public ResultsService(GameManager gameManager, TableView<QuizPlayerData> resultsTableView) {
        this.gameManager = gameManager;
        this.resultsTableView = resultsTableView;
    }

    public void getAndPutScoresInTable() throws Exception{

        QuizPlayerDataManager dataManager = gameManager.getQuizPlayerDataManagerFromJson();
        dataManager.quizPlayersData.sort(Comparator.comparingInt(QuizPlayerData::getCorrectQuestions).reversed());
        ObservableList<QuizPlayerData> playerScores = FXCollections.observableArrayList(dataManager.quizPlayersData);

        resultsTableView.setItems(playerScores);
    }
}
