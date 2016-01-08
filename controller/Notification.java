package controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import settings.Settings;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Chris on 1/2/2016.
 */
public class Notification {

    @FXML
    private ImageView logo;
    @FXML
    private Text text;
    @FXML
    private ToggleButton muteButton;

    private Stage stage;
    private static Settings settings = Settings.getInstance();




    /**
     *
     */
    @FXML
    private void initialize()
    {
        logo.setImage(new Image(getClass().getResource("/res/logo.png").toString()));
    }

    public void setText(String text)
    {
        this.text.setText(text);
    }

    @FXML
    public void muteNotification()
    {
        settings.setNotificationsMuted(muteButton.isSelected());
        stage.close();
    }


    public static void showNotification(String text, int length)
    {
        if (!settings.isNotificationsMuted()) {
            try {
                FXMLLoader loader = new FXMLLoader(Notification.class.getResource("Notification.fxml"));
                AnchorPane anchorPane = (AnchorPane) loader.load();
                Stage notificationStage = new Stage(StageStyle.UNDECORATED);
                Notification notification = (Notification) loader.getController();
                Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

                notificationStage.setScene(new Scene(anchorPane));
                notificationStage.alwaysOnTopProperty();
                notificationStage.setAlwaysOnTop(true);
                notificationStage.setX(screenDim.getWidth() - 272); //FIXME don't hard code width of notification
                notificationStage.setY(0);

                PauseTransition delay = new PauseTransition(Duration.millis(length));
                delay.setOnFinished(event -> notificationStage.close());
                notification.setText(text);
                notification.stage = notificationStage;
                notificationStage.show();
                delay.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
