package settings;

import controller.MainView;

import java.util.ArrayList;

/**
 * Created by Chris on 12/28/2015.
 */
public class Settings {

    private ArrayList<MailConfig> mailConfigs = new ArrayList<>();

    private MainView mainView;

    private static Settings ourInstance = new Settings();

    public static Settings getInstance() {

        if (ourInstance == null)
        {
            return new Settings();
        } else
        {
            return ourInstance;
        }

    }

    private Settings() {
        //TODO read from local file
        mailConfigs.add(new MailConfig("Gmail", "https://mail.google.com/", false));
        mailConfigs.add(new MailConfig("Calendar", "https://calendar.google.com/", false));
    }

    /**
     * Returns a COPY of the settings arraylist/
     * @return
     */
    public ArrayList<MailConfig> getMailConfigs() {
        return (ArrayList<MailConfig>)mailConfigs.clone();
    }

    public void addNewConifg(String name, String URL, boolean temporary)
    {
        mailConfigs.add(new MailConfig(name, URL, temporary));
    }
}
