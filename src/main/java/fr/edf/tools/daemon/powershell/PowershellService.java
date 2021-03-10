package fr.edf.tools.daemon.powershell;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PowershellService {

    private static final Logger logger = LoggerFactory.getLogger(PowershellService.class);

    /**
     * Execute the given PowerShell command
     * 
     * @param command : the powershell command to execute
     * @return
     */
    public ExecutionResult executePsCommand(String command) {
        String output;
        String error;
        Integer exitCode;
        String uncompiledCommand = uncompilePs(command);
        logger.info("Executing command {} ...", uncompiledCommand);
        if (!System.getProperty("os.name").contains("Windows")) {
            return new ExecutionResult(1, "",
                    "PowerShell daemon is not installed on a Windows machine");
        }
        try {
            // Getting the version
            // Executing the command
            Process powerShellProcess = Runtime.getRuntime().exec("powershell -encodedcommand" + command);
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

        } catch (IOException | InterruptedException e) {
            logger.error("An unexpected exception occured when performing the command " + command, e);
            return new ExecutionResult(1, "",
                    "An unexpected exception occured when performing the command " + e.getMessage());
        }
    }

    /**
     * Compile PowerShell script
     * 
     * @param encodedPs
     * @return encoded PowerShell
     */
    private String uncompilePs(String encodedPs) {
        byte[] cmd = Base64.getDecoder().decode(encodedPs);
        return new String(cmd, Charset.forName("UTF-16LE"));
    }

}
