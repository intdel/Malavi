package controller;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleListener;
import javafx.scene.control.Tab;

/**
 * Created by Chris on 12/29/2015.
 */
public class TitleChanged implements TitleListener {

    private Tab tab;
    private MainView mainView;

    public TitleChanged(Tab tab, MainView mainView) {
        this.tab = tab;
        this.mainView = mainView;
    }

    @Override
    public void onTitleChange(TitleEvent titleEvent) {
        //TODO spawn new notification
        //titleEvent.getTitle().contains()
        //Notification.showNotification(titleEvent.getTitle(), 2000);
        //FIXME

    }
}
