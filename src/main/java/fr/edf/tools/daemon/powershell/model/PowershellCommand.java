package fr.edf.tools.daemon.powershell.model;

public class PowershellCommand {

    private final boolean encoded;

    private final String command;

    public PowershellCommand(boolean encoded, String command) {
        this.encoded = encoded;
        this.command = command;
    }

    public boolean isEncoded() {
        return encoded;
    }

    public String getCommand() {
        return command;
    }

}
