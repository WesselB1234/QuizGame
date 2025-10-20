package Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GameService {

    private StackPane layout;

    public GameService(StackPane layout){
        this.layout = layout;
    }

    public void loadScene(String name, Object controller) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            fxmlLoader.setController(controller);

            Parent newContent = fxmlLoader.load();

            layout.getChildren().clear();
            layout.getChildren().add(newContent);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
