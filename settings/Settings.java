package settings;

import com.teamdev.jxbrowser.chromium.internal.FileUtil;
import controller.IntroDialog;
import controller.MainView;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Chris on 12/28/2015.
 */
public class Settings {

    private static Settings ourInstance = new Settings();
    private ArrayList<MailConfig> mailConfigs = new ArrayList<>();
    private MainView mainView;
    private String workDir;
    private String browserDir;
    private int notificationLength = 5000; //TODO make it variable
    private boolean notificationsMuted = false;

    /**
     * Creates the folder directory if it doesn't exist otherwise just loads the settings.
     */
    private Settings() {

        workDir = System.getProperty("user.home") + "\\Malavi";
        browserDir = workDir + "\\BrowserData";
        File workDirFile = new File(workDir);
        System.out.println(workDir);
        System.out.println(browserDir);

        //It's a new user, so create structure and example data
        if (!new File(workDir + "\\settings.inf").exists()) {
            IntroDialog.showIntro();
            workDirFile.mkdir();
            new File(browserDir).mkdir();

            boolean[] selectedExamples = IntroDialog.getSelectedExamples();
            if (selectedExamples[0]) {
                addNewConifg("GMail", "https://mail.google.com");
            }
            if (selectedExamples[1]) {
                addNewConifg("Google Calendar", "https://calendar.google.com");
            }
            if (selectedExamples[2]) {
                addNewConifg("Yahoo Mail", "https://mail.yahoo.com");
            }

        } else {
            loadSettings();
        }
    }

    public static Settings getInstance() {

        if (ourInstance == null) {
            return new Settings();
        } else {
            return ourInstance;
        }

    }

    public void setNotificationLength(int notificationLength) {
        this.notificationLength = notificationLength;
    }

    public void setNotificationsMuted(boolean notificationsMuted) {
        this.notificationsMuted = notificationsMuted;
    }

    public String getWorkDir() {
        return workDir;
    }

    public String getBrowserDir() {
        return browserDir;
    }

    public int getNotificationLength() {
        return notificationLength;
    }

    public boolean isNotificationsMuted() {
        return notificationsMuted;
    }

    /**
     * Returns a COPY of the settings arraylist/
     *
     * @return
     */
    public ArrayList<MailConfig> getMailConfigs() {
        return (ArrayList<MailConfig>) mailConfigs.clone();
    }

    private void loadSettings() {
        try {
            File settingsFile = new File(workDir + "\\settings.inf");
            BufferedReader settingsReader = new BufferedReader(new FileReader(settingsFile));
            Iterator<MailConfig> mailConfigIterator = mailConfigs.iterator();
            String currentLine;

            while ((currentLine = settingsReader.readLine()) != null) {
                mailConfigs.add(new MailConfig(currentLine, settingsReader.readLine()));
            }

            settingsReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSettingsToDisk() {
        try {
            File settingsFile = new File(workDir + "\\settings.inf");
            BufferedWriter settingsWriter = new BufferedWriter(new FileWriter(settingsFile));
            Iterator<MailConfig> mailConfigIterator = mailConfigs.iterator();
            while (mailConfigIterator.hasNext()) {
                MailConfig mailConfig = mailConfigIterator.next();
                settingsWriter.write(mailConfig.getName() + "\n");
                settingsWriter.write(mailConfig.getURL() + "\n");
            }
            settingsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetSettings() {
        FileUtil.deleteDir(new File(workDir)); //TODO still leaves empty directories behind
    }

    /**
     * Adds new config to the current settings, but it also saves it to disk right away.
     *
     * @param name
     * @param URL
     */
    public void addNewConifg(String name, String URL) {
        mailConfigs.add(new MailConfig(name, URL));
        saveSettingsToDisk();
    }

    /**
     * Removes config from settings, but also from the file.
     *
     * @param id
     */
    public void removeConfig(int id) {
        mailConfigs.remove(id);
        saveSettingsToDisk();
    }


}
