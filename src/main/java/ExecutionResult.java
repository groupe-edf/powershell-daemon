
public class ExecutionResult {

    private final Integer code;

    private final String output;

    public ExecutionResult(Integer code, String output) {
        this.code = code;
        this.output = output;
    }

    public Integer getCode() {
        return code;
    }

    public String getOutput() {
        return output;
    }

}
