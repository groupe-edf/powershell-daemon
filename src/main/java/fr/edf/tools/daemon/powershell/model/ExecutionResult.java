package fr.edf.tools.daemon.powershell.model;

/**
 * Represent the result of a PowerShell command execution
 * 
 * @author Mathieu Delrocq
 *
 */
public class ExecutionResult {

    /** exit code returned by the PowerShell command */
    private final Integer exitCode;

    /** Standard output of the PowerShell command */
    private final String output;

    /** Error output of the PowerShell command */
    private final String error;

    public ExecutionResult(Integer code, String output, String error) {
        super();
        this.exitCode = code;
        this.output = output;
        this.error = error;
    }

    /**
     * 
     * @return {@link #exitCode}
     */
    public Integer getCode() {
        return exitCode;
    }

    /**
     * 
     * @return {@link #output}
     */
    public String getOutput() {
        return output;
    }

    /**
     * 
     * @return {@link #error}
     */
    public String getError() {
        return error;
    }

}
