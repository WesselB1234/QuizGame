package Singletons;

import Models.QuizPlayerData;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

public class GameManager {

    private HashMap<String, QuizPlayerData> players;

    public GameManager () {
        players = new HashMap<>();
        System.out.println("www");
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

    public void setPlayerScore(String playerId, Integer score, Integer currentQuestion) throws Exception{

        if (players.containsKey(playerId)) {

            QuizPlayerData playerData = players.get(playerId);

            if (!playerData.correctQuestions.equals(score)){
                playerData.correctQuestions = score;
            }

            if(!playerData.questionsAnswered.equals(currentQuestion)){
                playerData.questionsAnswered = currentQuestion;
            }

            System.out.println(playerData.correctQuestions);
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
        QuizPlayerData playerData = new QuizPlayerData(newPlayerName, amountOfQuestions, 0, LocalDate.now());

        players.put(playerId, playerData);

        System.out.println(players.size());

        return playerId;
    }
}
