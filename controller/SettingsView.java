package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import settings.Settings;

import java.util.Optional;

/**
 * Created by Chris on 1/4/2016.
 */
public class SettingsView {

    @FXML
    private Text notificationHiddenText;
    @FXML
    private TextField notificationLengthText;
    @FXML
    private ToggleButton notificationHiddenButton;

    private Settings settings = Settings.getInstance();
    private Stage stage;
    private ObservableList<Tab> tabs;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTabs(ObservableList<Tab> tabs) {
        this.tabs = tabs;
    }

    @FXML
    private void initialize() {
        if (settings.isNotificationsMuted()) {
            notificationHiddenText.setText("Notifications are currently hidden until the next launch of Malavi.");
            notificationHiddenText.setFill(Color.RED);
            notificationHiddenButton.setSelected(true);
        } else {
            notificationHiddenText.setText("Notifications are currently showing.");
            notificationHiddenText.setFill(Color.BLACK);
            notificationHiddenButton.setSelected(false);
        }

        notificationLengthText.setText(Integer.toString(settings.getNotificationLength()));

        //To make sure user only inserts numbers and nothing negative
        notificationLengthText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                newValue = newValue.replaceAll("\\D+", "");
                if (!newValue.equalsIgnoreCase("") && Integer.parseInt(newValue) <= 0 || notificationLengthText.getText().length() == 0) {
                    notificationLengthText.setText("0");
                } else {
                    notificationLengthText.setText(newValue);
                }
            }
        });
    }


    @FXML
    private void applySettings() {
        settings.setNotificationLength(Integer.parseInt(notificationLengthText.getText()));
        settings.setNotificationsMuted(notificationHiddenButton.isSelected());
        stage.close();
    }

    @FXML
    private void muteNotifications() {
        if (notificationHiddenButton.isSelected()) {
            notificationHiddenText.setText("Notifications are currently hidden until the next launch of Malavi.");
            notificationHiddenText.setFill(Color.RED);
        } else {
            notificationHiddenText.setText("Notifications are currently showing.");
            notificationHiddenText.setFill(Color.BLACK);
        }
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    @FXML
    private void resetToDefault()
    {
        notificationLengthText.setText("5000");
        notificationHiddenButton.setSelected(false);
        notificationHiddenText.setText("Notifications are currently showing.");
        notificationHiddenText.setFill(Color.BLACK);
    }

    /**
     * Resets settings and allows user to start from scratch. It deletes all files in the user directory, but it leaves the structure behind.
     */
    @FXML
    private void resetSettings() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> deleteResult;
        deleteAlert.setTitle("Resetting all files and settings");
        deleteAlert.setHeaderText("Are you sure you want to remove everything\nand start from scratch?");
        deleteAlert.setContentText("Your working directory is (in case you want to back it up first): " + Settings.getInstance().getWorkDir());
        stage.setAlwaysOnTop(false);
        deleteResult = deleteAlert.showAndWait();


        if (deleteResult.get() == ButtonType.OK) {
            stage.hide();
            tabs.remove(0, tabs.size());
            Settings.getInstance().resetSettings();
            Alert restartAlert = new Alert(Alert.AlertType.INFORMATION);
            restartAlert.setTitle("Settings deleted");
            restartAlert.setHeaderText("Settings have been deleted.\nYou need to restart Malavi now.");
            restartAlert.setContentText("NOTE: New settings will be stored in:\n" + Settings.getInstance().getWorkDir());
            restartAlert.showAndWait();
            Platform.exit();
        }

        stage.setAlwaysOnTop(true);

    }


}
