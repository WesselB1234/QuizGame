package Services;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameService {

    private final VBox layout;

    public GameService(VBox layout){
        this.layout = layout;
    }

    public void loadScene(String name, Object controller) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            fxmlLoader.setController(controller);

            Parent newContent = fxmlLoader.load();

            layout.getChildren().clear();
            layout.getChildren().add(newContent);

            Platform.runLater(() -> {
                Scene scene = layout.getScene();
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
                Stage stage = (Stage) scene.getWindow();
                stage.sizeToScene();
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
