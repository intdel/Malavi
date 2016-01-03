package controller;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import settings.MailConfig;
import settings.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

/**
 * Created by Chris on 12/28/2015.
 * The MainView has everything!
 */
public class MainView {

    @FXML
    private TabPane tabPane;

    private Stage stage;
    private ObservableList<Tab> tabs;
    private Settings settings = Settings.getInstance();
    private int tabIndex = -1;

    /**
     * Necessary, so that we can change stuff like title etc. Will be called by the object/class that launches a new stage with this scene.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        ArrayList<MailConfig> mailConfigs = settings.getMailConfigs();
        Iterator<MailConfig> mailConfigIterator = mailConfigs.iterator();
        tabs = tabPane.getTabs();

        while (mailConfigIterator.hasNext()) {
            MailConfig currentConfig = mailConfigIterator.next();
            addClient(currentConfig.getName(), currentConfig.getURL());
        }


    }

    /**
     * One of the menu items that open a new window to add a new tab ('client')
     */
    @FXML
    private void openAddNewMailClientDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewMailClientDialog.fxml"));
            AnchorPane newMailClient = (AnchorPane) loader.load();
            Stage newMailClientStage = new Stage(StageStyle.UTILITY);
            newMailClientStage.setTitle("Add new mail client");
            newMailClientStage.setResizable(false);
            newMailClientStage.setAlwaysOnTop(true);
            newMailClientStage.setScene(new Scene(newMailClient));
            ((NewMailClientDialog) loader.getController()).setMainView(this);
            ((NewMailClientDialog) loader.getController()).setMyStage(newMailClientStage);
            newMailClientStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a client/tab to the MainView.
     *
     * @param name Name of the client (will be part of the tab title)
     * @param URL  URL of the client (will be part of the tab title as well as used to load the client)
     */
    public void addClient(String name, String URL) {
        BorderPane browserPane = new BorderPane();
        Tab newTab = new Tab();
        tabIndex++;
        newTab.setId(Integer.toString(tabIndex));

        newTab.setText(name + " - " + URL);
        newTab.setContent(browserPane);

        newTab.setClosable(true);

        tabs.add(newTab);

        Browser browser = new Browser(new BrowserContext(new BrowserContextParams(Settings.getInstance().getBrowserDir())));
        BrowserView browserView = new BrowserView(browser);
        browserPane.setCenter(browserView);
        browser.loadURL(URL);
        browser.addTitleListener(new TitleChanged(newTab, this));
    }

    /**
     * Finds which tab is currently selected. Used for closing, refresh etc.
     *
     * @return Reference to currently active tab
     */
    private Tab findSelectedTab() {
        Iterator<Tab> tabIterator = tabs.iterator();
        Tab currentTab;
        while (tabIterator.hasNext()) {
            currentTab = tabIterator.next();
            if (currentTab.isSelected()) {
                return currentTab;
            }
        }

        return null;
    }

    /**
     * Refreshes a tab
     *
     * @param tab Tab that should be refreshed.
     */
    private void refreshTab(Tab tab) {
        if (tab != null) {
            BorderPane borderPane = (BorderPane) tab.getContent();
            BrowserView browserView = (BrowserView) borderPane.getCenter();
            browserView.getBrowser().reload();
        }
    }

    @FXML
    private void refreshTab() {
        refreshTab(findSelectedTab());
    }

    @FXML
    private void refreshAllTabs() {
        Iterator<Tab> tabIterator = tabs.iterator();
        while (tabIterator.hasNext()) {
            refreshTab(tabIterator.next());
        }
    }

    /**
     * Removes currently selected tab.
     */
    @FXML
    private void removeTab() {
        Tab selectedTab = findSelectedTab();
        Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> closeAlertResult;
        if (selectedTab!= null) {
            closeAlert.setTitle("Close tab");
            closeAlert.setHeaderText("Are you sure you want to close tab \"" + selectedTab.getText() + "\"?");
            closeAlertResult = closeAlert.showAndWait();
            if (closeAlertResult.get() == ButtonType.OK) {
                settings.removeConfig(Integer.parseInt(selectedTab.getId()));
                tabs.remove(selectedTab);
                renumberTabIDs();
            }
        }
    }

    /**
     * Used to renumber tabIDs so that when removing, updating, tabs etc. no ID mismatch will happen with the mailconfigs array
     */
    private void renumberTabIDs() {
        Iterator<Tab> tabIterator = tabs.iterator();
        int id = 0;
        while (tabIterator.hasNext()) {
            tabIterator.next().setId(Integer.toString(id));
            id++;
        }
    }

    /**
     * Just for debugging. Can be removed.
     */
    @FXML
    private void debugShowIntro() {
        Notification.showNotification("Test notification for 2 seconds", 2000);
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
            stage.close();
        }

    }


}
