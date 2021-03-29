package fr.edf.tools.daemon.powershell.model;

public class User {

    private final String username;

    private final String password;

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
