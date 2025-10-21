package Services;

import Models.QuizGame;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {

    @Test
    public void testPagesCountInQuizJson(){

        Integer pageCount = 0;

        try{
            String projectRoot = System.getProperty("user.dir");
            String resultsFolderDir = projectRoot + "\\src\\main\\JSONs\\QuizResults\\";
            MenuService menuService = new MenuService(resultsFolderDir);

            QuizGame quizGame = menuService.getQuizGameFromJson(new File(resultsFolderDir + "..\\" + "QuizJson1.JSON"));
            pageCount = quizGame.pages.size();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(2, pageCount);
    }
}