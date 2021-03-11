package fr.edf.tools.daemon.powershell.model;

/**
 * Represent a PowerShell command
 * 
 * @author Mathieu Delrocq
 *
 */
public class PowershellCommand {

    /** flag to know if the command is encoded or not */
    private final boolean encoded;

    /** PowerShell command */
    private final String command;

    public PowershellCommand(boolean encoded, String command) {
        this.encoded = encoded;
        this.command = command;
    }

    /**
     * 
     * @return {@link #encoded}
     */
    public boolean isEncoded() {
        return encoded;
    }

    /**
     * 
     * @return {@link #command}
     */
    public String getCommand() {
        return command;
    }

}
