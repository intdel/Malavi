package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Chris on 12/29/2015.
 * Is showed when Malavi is started for the first time (e.g. when settings file does not exist).
 */
public class IntroDialog {

    /**
     * Array that is used for the example tabs that can be selected on first run
     */
    private static boolean[] selectedExamples = {false, false, false};
    private static Stage introStage;
    @FXML
    private ImageView logo;
    @FXML
    private CheckBox gMailCheckBox;
    @FXML
    private CheckBox gCalendarCheckBox;
    @FXML
    private CheckBox yahooMailCheckBox;
    private String selectedFolder;

    public static boolean[] getSelectedExamples() {
        return selectedExamples;
    }

    /**
     * Called by other classes if the intro dialog needs to be shown. Most likley only by Settings.
     */
    public static void showIntro() {
        try {
            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("IntroDialog.fxml"));
            AnchorPane introPane = (AnchorPane) loader.load();
            introStage = new Stage(StageStyle.UNDECORATED);
            introStage.setTitle("Malavi Intro Wizard");
            introStage.setResizable(false);
            introStage.getIcons().add(new Image(IntroDialog.class.getResource("/res/logo.png").toString()));
            introStage.setScene(new Scene(introPane));
            introStage.setAlwaysOnTop(true);
            introStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toString()));
    }

    /**
     * Applies selection of checkboxes on static array so that the MainView can know which tabs to open
     */
    @FXML
    private void nextButton() {
        if (gMailCheckBox.isSelected()) {
            selectedExamples[0] = true;
        }
        if (gCalendarCheckBox.isSelected()) {
            selectedExamples[1] = true;
        }
        if (yahooMailCheckBox.isSelected()) {
            selectedExamples[2] = true;
        }
        introStage.close();
    }
}
