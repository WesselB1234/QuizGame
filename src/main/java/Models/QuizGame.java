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
    @JsonSubTypes.Type(value = Page.class, name = "page"),
    @JsonSubTypes.Type(value = QuizGame.class, name = "quizGame"),
})

public class QuizGame {

    public String quizId;
    public String title;
    public String description;
    public ArrayList<Page> pages;
    public QuizPlayerDataManager quizPlayerDataManager;
}