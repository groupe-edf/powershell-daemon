package fr.edf.tools.daemon.powershell.model;

public class JnlpProcess {

    private final String jenkinsUrl;

    private final String secret;

    private final User user;

    public JnlpProcess(String jenkinsUrl, String username, String secret, User user) {
        super();
        this.jenkinsUrl = jenkinsUrl;
        this.secret = secret;
        this.user = user;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public String getSecret() {
        return secret;
    }

    public User getUser() {
        return user;
    }

}
