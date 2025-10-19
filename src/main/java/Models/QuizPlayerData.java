package Models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = QuizPlayerData.class, name = "quizPlayerData"),
})

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

    public QuizPlayerData() {}

    public String getPlayerName() {
        return playerName;
    }

    public Integer getQuestionsAnswered() {
        return questionsAnswered;
    }

    public Integer getCorrectQuestions() {
        return correctQuestions;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }
}
