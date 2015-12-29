package controller;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleListener;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import settings.MailConfig;
import settings.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Chris on 12/28/2015.
 */
public class MainView {

    @FXML
    private TabPane tabPane;

    private Stage stage;
    private ObservableList<Tab> tabs;
    private Settings settings = Settings.getInstance();

    public MainView()
    {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize()
    {
        ArrayList<MailConfig> mailConfigs = settings.getMailConfigs();
        Iterator<MailConfig> mailConfigIterator = mailConfigs.iterator();
        tabs = tabPane.getTabs();

        while (mailConfigIterator.hasNext())
        {
            MailConfig currentConfig = mailConfigIterator.next();
            addClient(currentConfig.getName(), currentConfig.getURL());
        }


    }

    @FXML
    private void openAddNewMailClientDialog()
    {
        try {
            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("NewMailClientDialog.fxml"));
            AnchorPane newMailClient = (AnchorPane) loader.load();
            Stage newMailClientStage = new Stage(StageStyle.UTILITY);
            newMailClientStage.setTitle("Add new mail client");
            newMailClientStage.setResizable(false);
            newMailClientStage.setScene(new Scene(newMailClient));
            ((NewMailClientDialog) loader.getController()).setMainView(this);
            ((NewMailClientDialog) loader.getController()).setMyStage(newMailClientStage);
            newMailClientStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient(String name, String URL)
    {
        BorderPane browserPane = new BorderPane();
        Tab newTab = new Tab();

        newTab.setText(name + " - " + URL);
        newTab.setContent(browserPane);

        newTab.setClosable(true);

        tabs.add(newTab);

        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        browserPane.setCenter(browserView);
        browser.loadURL(URL);
    }

}
