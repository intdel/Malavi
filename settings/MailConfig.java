package settings;

/**
 * Created by Chris on 12/28/2015.
 */
public class MailConfig {

    private String name;
    private String URL;
    boolean temporary;

    public MailConfig(String name, String URL, boolean temporary) {
        this.name = name;
        this.URL = URL;
        this.temporary = temporary;
    }

    public String getName() {
        return name;
    }

    public String getURL() {
        return URL;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }
}
