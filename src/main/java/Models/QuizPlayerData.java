package Models;

import java.time.LocalDateTime;

public class QuizPlayerData {

    public String playerName;
    public Integer questionsAnswered;
    public Integer correctQuestions;
    public LocalDateTime joinDate;

    public QuizPlayerData(String playerName, Integer questionsAnswered, Integer correctQuestions, LocalDateTime joinDate) {
        this.playerName = playerName;
        this.questionsAnswered = questionsAnswered;
        this.correctQuestions = correctQuestions;
        this.joinDate = joinDate;
    }
}
