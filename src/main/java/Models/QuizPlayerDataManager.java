package Models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = QuizPlayerDataManager.class, name = "quizPlayerDataManager"),
})

public class QuizPlayerDataManager {

    public String quizId;
    public String quizName;
    public ArrayList<QuizPlayerData> quizPlayersData;
}
