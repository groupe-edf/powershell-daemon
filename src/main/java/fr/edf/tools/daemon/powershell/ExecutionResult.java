package fr.edf.tools.daemon.powershell;

public class ExecutionResult {

    private final Integer exitCode;

    private final String output;

    private final String error;

    public ExecutionResult(Integer code, String output, String error) {
        this.exitCode = code;
        this.output = output;
        this.error = error;
    }

    public Integer getCode() {
        return exitCode;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }

}
