package Models;

import java.time.LocalDate;
import java.util.Date;

public class QuizPlayerData {

    public String playerName;
    public Integer questionsAnswered;
    public Integer correctQuestions;
    public LocalDate joinDate;

    public QuizPlayerData(String playerName, Integer questionsAnswered, Integer correctQuestions, LocalDate joinDate) {
        this.playerName = playerName;
        this.questionsAnswered = questionsAnswered;
        this.correctQuestions = correctQuestions;
        this.joinDate = joinDate;
    }
}
