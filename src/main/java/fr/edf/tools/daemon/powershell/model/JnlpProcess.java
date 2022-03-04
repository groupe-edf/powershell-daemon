package fr.edf.tools.daemon.powershell.model;

public class JnlpProcess {

    private final String jenkinsUrl;

    private final String secret;

    private final String agentJvmParameters;

    private final User user;

    public JnlpProcess(String jenkinsUrl, String secret, String agentJvmParameters, User user) {
        super();
        this.jenkinsUrl = jenkinsUrl;
        this.secret = secret;
        this.user = user;
        this.agentJvmParameters = agentJvmParameters;
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

    public String getAgentJvmParameters() {
        return agentJvmParameters;
    }

}
