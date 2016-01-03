package controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by Chris on 1/2/2016.
 */
public class Notification {

    @FXML
    private ImageView logo;
    @FXML
    private Text text;

    private Stage stage;


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


    public static void showNotification(String text, int length)
    {
        try {
            FXMLLoader loader = new FXMLLoader(Notification.class.getResource("Notification.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            Stage notificationStage = new Stage(StageStyle.UNDECORATED);
            Notification notification = (Notification)loader.getController();
            notificationStage.setScene(new Scene(anchorPane));
            notificationStage.alwaysOnTopProperty();
            notificationStage.setAlwaysOnTop(true);
            PauseTransition delay = new PauseTransition(Duration.millis(length));
            delay.setOnFinished(event -> notificationStage.close());
            notification.setText(text);
            notificationStage.show();
            delay.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
