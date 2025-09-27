package Models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // use "type" field to identify subclass
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Page.class, name = "page"),
    @JsonSubTypes.Type(value = QuizGame.class, name = "quizGame"),
})
// C:\Development\ProjectJavaFundamentals\src\main\JSONs\QuizJson1.JSON
public class QuizGame {

    public Integer quizId;
    public String title;
    public String description;
    public ArrayList<Page> pages;
    public QuizPlayerDataManager quizPlayerDataManager;
}