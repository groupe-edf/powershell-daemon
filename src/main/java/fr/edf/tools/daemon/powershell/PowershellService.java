package fr.edf.tools.daemon.powershell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.edf.tools.daemon.powershell.model.ExecutionResult;
import fr.edf.tools.daemon.powershell.utils.Constants;

/**
 * Service : Perform PowerShell actions
 * 
 * @author Mathieu Delrocq
 *
 */
@Service
public class PowershellService {

    private static final Logger logger = LoggerFactory.getLogger(PowershellService.class);

    /**
     * Execute the given PowerShell command
     * 
     * @param command : the PowerShell command to execute
     * @param encoded : flag to know if the input command is encoded or not
     * 
     * @return {@link ExecutionResult}
     */
    public ExecutionResult executePsCommand(String command, boolean encoded) {
        String output;
        String error;
        Integer exitCode;
        String uncompiledCommand = encoded ? uncompilePs(command) : command;
        command = encoded ? command : compilePs(command);
        if (!System.getProperty(Constants.OS_PROPERTY).contains(Constants.WINDOWS)) {
            return new ExecutionResult(1, "", "PowerShell daemon is not installed on a Windows machine");
        }
        try {
            // Getting the version
            // Executing the command
            logger.info("Executing command {} ...", uncompiledCommand);
            Process powerShellProcess = Runtime.getRuntime().exec("powershell -encodedcommand " + command);
            // Getting the results
            logger.trace("Get exit code");
            exitCode = powerShellProcess.waitFor();
            logger.trace("Get outputStream");
            powerShellProcess.getOutputStream().close();

            // Get Standard Output
            output = safetyReadInputStream(powerShellProcess.getInputStream());
            logger.debug("Standard Output: \n {}", output);

            // Get Error Output
            logger.trace("Get outputStream");
            error = safetyReadInputStream(powerShellProcess.getErrorStream());
            logger.debug("Standard Error: \n {}", error);

            logger.info("Command {} executed !", uncompiledCommand);

            return new ExecutionResult(exitCode, output, error);

        } catch (IOException e) {
            logger.error(String.format(Constants.UNEXPECTED_ERROR_MESSAGE, command), e);
            return new ExecutionResult(1, "", String.format(Constants.UNEXPECTED_ERROR_MESSAGE, e.getMessage()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(String.format(Constants.UNEXPECTED_ERROR_MESSAGE, command), e);
            return new ExecutionResult(1, "", String.format(Constants.UNEXPECTED_ERROR_MESSAGE, e.getMessage()));
        }
    }

    /**
     * Uncompile PowerShell command to make it readable<br>
     * Used for loggers
     * 
     * @param encodedPs
     * @return Decoded PowerShell command
     */
    private String uncompilePs(String encodedPs) {
        byte[] cmd = Base64.getDecoder().decode(encodedPs);
        return new String(cmd, StandardCharsets.UTF_16LE);
    }

    /**
     * Compile PowerShell script
     * 
     * @param psScript
     * @return encoded PowerShell
     */
    private String compilePs(String psScript) {
        byte[] cmd = psScript.getBytes(StandardCharsets.UTF_16LE);
        return Base64.getEncoder().encodeToString(cmd);
    }

    /**
     * Return the given InputStream as String <br>
     * Avoid potential blocking Thread when reading the InputStream
     * 
     * @param in : the given InputStream
     * @return String value of in
     * @throws InterruptedException
     */
    private String safetyReadInputStream(InputStream in) throws InterruptedException {
        InputStreamReaderThread inputStreamReaderThread = new InputStreamReaderThread();
        inputStreamReaderThread.setIn(in);
        inputStreamReaderThread.start();
        inputStreamReaderThread.join(5000); // 5 seconds max to read the InputStream
        inputStreamReaderThread.interrupt();
        return inputStreamReaderThread.getResult();
    }

    /**
     * Thread to read an InputStream and have the control on potential blocking
     * issue
     * 
     * @author Mathieu Delrocq
     *
     */
    private class InputStreamReaderThread extends Thread {

        private InputStream in;
        private String result = "";

        public String getResult() {
            return result;
        }

        public void setIn(InputStream in) {
            this.in = in;
        }

        public void run() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result += reader.lines().collect(Collectors.joining("\n"));
            try {
                reader.close();
            } catch (IOException e) {
                logger.debug("Cannot close the reader but it will be deleted with the Thread {}", this.getName());
            }
        }
    }

}
