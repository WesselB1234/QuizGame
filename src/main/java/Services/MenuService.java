package Services;

import Models.QuizGame;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class MenuService {

    private final String resultsFolderDir;

    public MenuService(String resultsFolderDir) {
        this.resultsFolderDir = resultsFolderDir;
    }

    public QuizGame getQuizGameFromJson(File file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        QuizGame quizGame = mapper.readValue(file, QuizGame.class);
        quizGame.quizId = generateQuizId();

        return quizGame;
    }

    public String generateQuizId(){

        Random rand = new Random();
        StringBuilder quizId = new StringBuilder();

        for(int i = 0; i < 5; i++){
            quizId.append(Integer.toString(rand.nextInt(10)));
        }

        Path path = Paths.get(resultsFolderDir + quizId + "-results.json");

        if (Files.exists(path)) {
            return generateQuizId();
        }

        return quizId.toString();
    }
}
