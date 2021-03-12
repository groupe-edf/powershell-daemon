package fr.edf.tools.daemon.powershell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.edf.tools.daemon.powershell.model.ExecutionResult;

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
        logger.info("Executing command {} ...", uncompiledCommand);
        if (!System.getProperty(Constants.OS_PROPERTY).contains(Constants.WINDOWS)) {
            return new ExecutionResult(1, "", "PowerShell daemon is not installed on a Windows machine");
        }
        try {
            // Getting the version
            // Executing the command
            Process powerShellProcess = Runtime.getRuntime().exec("powershell -encodedcommand " + command);
            // Getting the results
            exitCode = powerShellProcess.waitFor();
            powerShellProcess.getOutputStream().close();

            // Get Standard Output
            BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
            output = stdout.lines().collect(Collectors.joining("\n"));
            stdout.close();
            logger.debug("Standard Output: \n {}", output);

            // Get Error Output
            BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
            error = stderr.lines().collect(Collectors.joining("\n"));
            stderr.close();
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

}
