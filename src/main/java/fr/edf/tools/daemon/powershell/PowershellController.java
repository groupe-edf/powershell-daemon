package fr.edf.tools.daemon.powershell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for all PowerShell requests
 * @author Mathieu Delrocq
 *
 */
@RestController
public class PowershellController {

    @Autowired
    PowershellService psService;

    /**
     * Execute the given PowerShell command
     * @param command: the encoded command
     * @return {@link ExecutionResult}
     */
    @PostMapping("/executePsCommand")
    public ExecutionResult executePsCommand(@RequestParam(value = "command") String command) {
        return psService.executePsCommand(command);
    }

}
