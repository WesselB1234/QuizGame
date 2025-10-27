package Singletons;

import Controllers.Interfaces.IScoresUploadNotifier;
import Models.QuizGame;
import Models.QuizPlayerData;
import Models.QuizPlayerDataManager;
import Observers.ScoresUploadObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameManager {

    private final QuizGame quizGame;
    private final HashMap<String, QuizPlayerData> players = new HashMap<>();
    private final String resultsJsonDir;
    private final String resultsCsvDir;
    private final ScoresUploadObserver scoresUploadObserver = new ScoresUploadObserver();
    private Boolean isPracticeMode;

    public GameManager (QuizGame quizGame, String resultsFolderDir) {
        this.quizGame = quizGame;
        resultsJsonDir = resultsFolderDir + quizGame.quizId + "-results.json";
        resultsCsvDir = "C:\\Development\\ProjectJavaFundamentals\\src\\main\\CSVs\\" + quizGame.quizId + "-results.csv";;
    }

    private String generatePlayerId(){

        Random rand = new Random();
        StringBuilder playerId = new StringBuilder();

        for(int i = 0; i < 5; i++){
            playerId.append(Integer.toString(rand.nextInt(10)));
        }

        if (players.containsKey(playerId.toString())){
            return generatePlayerId();
        }

        return playerId.toString();
    }

    private Boolean isNameAlreadyExists(String name){

        for(QuizPlayerData player: players.values()){
            if (name.equals(player.playerName)){
                return true;
            }
        }

        return false;
    }

    public void setPlayerScore(String playerId, Integer score, Integer currentQuestionNumber) throws Exception{

        if (players.containsKey(playerId)) {

            QuizPlayerData playerData = players.get(playerId);

            if (!playerData.correctQuestions.equals(score)){
                playerData.correctQuestions = score;
            }

            if(!playerData.questionsAnswered.equals(currentQuestionNumber)){
                playerData.questionsAnswered = currentQuestionNumber;
            }
        }
        else{
            throw new Exception("Player not found.");
        }
    }

    public String addPlayerToQuizPlayers(String newPlayerName, Integer amountOfQuestions) throws Exception {

        if (isNameAlreadyExists(newPlayerName)) {
            throw new Exception("Name must be unique.");
        }

        String playerId = generatePlayerId();
        QuizPlayerData playerData = new QuizPlayerData(newPlayerName, 0, 0, LocalDateTime.now());

        players.put(playerId, playerData);

        return playerId;
    }

    private void setPlayerDataManagerToJson(QuizPlayerDataManager dataManager) throws IOException{

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(new File(resultsJsonDir), dataManager);
    }

    public void exportResultsToCsv() throws IOException{

        try (FileWriter writer = new FileWriter(resultsCsvDir)) {

            QuizPlayerDataManager dataManager = getQuizPlayerDataManagerFromJson();

            for (QuizPlayerData quizPlayerData : dataManager.quizPlayersData) {


                String name = quizPlayerData.playerName;
                Integer questionsAnswered = quizPlayerData.questionsAnswered;
                Integer questionsCorrect = quizPlayerData.correctQuestions;
                LocalDateTime joinDate = quizPlayerData.joinDate;

                writer.write(String.format("%s;%s;%d;%d;%s%n",
                    dataManager.quizName,
                    name,
                    questionsAnswered,
                    questionsCorrect,
                    joinDate
                ));
            }

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void saveScores() throws IOException{

        QuizPlayerDataManager dataManager = new QuizPlayerDataManager();
        ArrayList<QuizPlayerData> quizPlayersData = new ArrayList<>(players.values());

        dataManager.quizId = quizGame.quizId;
        dataManager.quizName = quizGame.title;
        dataManager.quizPlayersData = quizPlayersData;

        setPlayerDataManagerToJson(dataManager);
        scoresUploadObserver.notifyNotifiers();
    }

    public QuizPlayerDataManager getQuizPlayerDataManagerFromJson() throws IOException{

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(new File(resultsJsonDir), QuizPlayerDataManager.class);
    }

    public QuizGame getQuizGame() {
        return quizGame;
    }

    public void setPracticeMode(boolean isPracticeMode){
        this.isPracticeMode = isPracticeMode;
    }

    public boolean getIsPracticeMode(){
        return isPracticeMode;
    }

    public void subscribeNotifierToScoresObserver(IScoresUploadNotifier notifier){
        scoresUploadObserver.subscribeNotifier(notifier);
    }
}
