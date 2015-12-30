package settings;

/**
 * Created by Chris on 12/28/2015.
 */
public class MailConfig {

    private String name;
    private String URL;

    public MailConfig(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public String getURL() {
        return URL;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

}
