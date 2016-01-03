package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import settings.Settings;

/**
 * Created by Chris on 12/28/2015.
 */
public class NewMailClientDialog {

    private Settings settings = Settings.getInstance();
    private MainView mainView;
    private Stage myStage;

    @FXML
    private TextField name;
    @FXML
    private TextField URL;


    /**
     * Necessary, so that we can add a new client/tab
     * @param mainView
     */
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Necessary, so that we can close the stage if we choose to cancel.
     * @param myStage
     */
    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private int addNewClient() {

        if (name.getLength() == 0 || URL.getLength() == 0 || (URL.getText().contains("http") == false)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please double check your entries.");
            alert.setHeaderText("Error while adding new mail client!");
            alert.showAndWait();
            return -1;
        }

        settings.addNewConifg(name.getText(), URL.getText());

        mainView.addClient(name.getText(), URL.getText());

        myStage.close();
        return 0;

    }

    @FXML
    private void cancel() {
        myStage.close();
    }
}
