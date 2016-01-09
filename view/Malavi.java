package view;

import controller.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Chris on 12/28/2015.
 */
public class Malavi extends Application {

    private Stage stage;


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/MainView.fxml"));
        AnchorPane mainPane = (AnchorPane) loader.load();
        Scene mainScene = new Scene(mainPane);
        MainView mainView = (MainView)loader.getController();
        mainView.setStage(stage);

        stage.setScene(mainScene);
        stage.setTitle("Malavi - V1.1");
        stage.getIcons().add(new Image(getClass().getResource("/res/logo.png").toString()));
        stage.show();
    }

    public void setTitle(String title)
    {
        stage.setTitle(title);
    }


}
