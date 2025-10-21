package Services;

import javafx.scene.control.Label;

public class ErrorHandlerService {

    private final Label errorLbl;

    public ErrorHandlerService(Label errorLbl) {
        this.errorLbl = errorLbl;
    }

    public void displayErrorMessage(String errorMessage){

        if (!errorLbl.isVisible()){
            errorLbl.setVisible(true);
        }
        errorLbl.setText("An error has occurred: " + errorMessage);
    }
}
