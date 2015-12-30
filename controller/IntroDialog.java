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
import settings.Settings;

import java.io.IOException;

/**
 * Created by Chris on 12/29/2015.
 */
public class IntroDialog {

    @FXML
    private ImageView logo;
    @FXML
    private CheckBox gMailCheckBox;
    @FXML
    private CheckBox gCalendarCheckBox;
    @FXML
    private CheckBox yahooMailCheckBox;

    private static boolean[] selectedExamples =  {false, false, false};

    public static boolean[] getSelectedExamples() {
        return selectedExamples;
    }

    private String selectedFolder;
    private static Stage introStage;

    @FXML
    private void initialize()
    {
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toString()));
    }

    @FXML
    private void nextButton()
    {
        if (gMailCheckBox.isSelected())
        {
            selectedExamples[0] = true;
        }
        if (gCalendarCheckBox.isSelected())
        {
            selectedExamples[1] = true;
        }
        if (yahooMailCheckBox.isSelected())
        {
            selectedExamples[2] = true;
        }
        introStage.close();
    }

    public static void showIntro()
    {
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
}
