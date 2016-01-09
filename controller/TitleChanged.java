package controller;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleListener;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import settings.Settings;

/**
 * Created by Chris on 12/29/2015.
 */
public class TitleChanged implements TitleListener {

    private int messageCounter = -1;
    private String clientName;
    private Settings settings = Settings.getInstance();
    private boolean isFirstNotification = true; //Used to check if it would be the first load so that it will skip the first notification


    public TitleChanged(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Spawns a notification in the corner if the changed title includes numbers within parantheses (Gmail has this for sure)
     * @param titleEvent
     */
    @Override
    public void onTitleChange(TitleEvent titleEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String message = clientName + " seems to have a new email for you!";
                String title = titleEvent.getTitle();
                String debugtitle = title.replaceAll("\\D+","");
                int newMessageCount = -1;
                if (!debugtitle.contentEquals(""))
                {
                   newMessageCount = Integer.parseInt(title.replaceAll("\\D+",""));
                } else
                {
                    messageCounter = -1;
                }

                if (title.contains("(") && title.contains(")") && messageCounter < newMessageCount && !isFirstNotification) {
                    Notification.showNotification(message, settings.getNotificationLength());
                    messageCounter = newMessageCount;
                }
                if (title.contains("(") && title.contains(")") && messageCounter < newMessageCount)
                {
                    isFirstNotification = false;
                }

            }
        });



    }
}
