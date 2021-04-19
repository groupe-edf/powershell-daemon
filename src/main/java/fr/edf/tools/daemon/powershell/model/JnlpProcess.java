package fr.edf.tools.daemon.powershell.model;

public class JnlpProcess {

    private final String jenkinsUrl;

    private final String username;

    private final String secret;

    public JnlpProcess(String jenkinsUrl, String username, String secret) {
        super();
        this.jenkinsUrl = jenkinsUrl;
        this.username = username;
        this.secret = secret;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getSecret() {
        return secret;
    }

}
