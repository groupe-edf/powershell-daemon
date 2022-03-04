package fr.edf.tools.daemon.powershell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.edf.tools.daemon.powershell.PowershellService;
import fr.edf.tools.daemon.powershell.model.ExecutionResult;
import fr.edf.tools.daemon.powershell.model.JnlpProcess;
import fr.edf.tools.daemon.powershell.model.User;
import fr.edf.tools.daemon.powershell.utils.Constants;

@RestController
@RequestMapping("api")
public class PowershellStandardController {

    @Autowired
    PowershellService psService;

    @GetMapping("/whoami")
    public ExecutionResult whoami() {
        return psService.executePsCommand(Constants.WHOAMI, false);
    }

    @GetMapping("/user")
    public ExecutionResult getUser(@RequestParam String username) {
        return psService.executePsCommand(String.format(Constants.CHECK_USER_EXIST, username), false);
    }

    @PostMapping("/user/create")
    public ExecutionResult createUser(@RequestBody User user) {
        ExecutionResult result = psService.executePsCommand(
                String.format(Constants.CREATE_USER, user.getUsername(), user.getPassword(), user.getUsername()),
                false);
        if (result.getCode().intValue() == 0) {
            // Execute a command to init the workdir of the user
            result = initUserWorkdir(user);
        }
        return result;
    }

    @PostMapping("/user/delete")
    public ExecutionResult deleteUser(@RequestParam String username) {
        ExecutionResult result = stopUserProcess(username);
        if (result.getCode().intValue() == 0) {
            result = psService.executePsCommand(String.format(Constants.DELETE_USER, username), false);
        }
        if (result.getCode().intValue() == 0) {
            // remove profil will remove the wordir
            result = removeOrphanedProfil();
        }
        return result;
    }

    @PostMapping("/user/workdir/init")
    public ExecutionResult initUserWorkdir(@RequestBody User user) {
        return psService.executePsCommand(String.format(Constants.INIT_WORKDIR, user.getUsername(), user.getPassword()),
                false);
    }

    @PostMapping("/profil/orphaned/remove")
    public ExecutionResult removeOrphanedProfil() {
        return psService.executePsCommand(Constants.REMOVE_ORPHANED_PROFIL, false);
    }

    @PostMapping("/user/remoting")
    public ExecutionResult getRemoting(@RequestParam String remotingUrl, @RequestBody User user) {
        String remotingCommand = String.format(Constants.GET_REMOTING_JAR, remotingUrl);
        return psService.executePsCommand(String.format(Constants.START_PROCESS_RUN_AS, user.getUsername(),
                user.getPassword(), user.getUsername(), remotingCommand), false);
    }

    @PostMapping("/user/jnlp")
    public ExecutionResult runJnlp(@RequestBody JnlpProcess jnlpProcess) {
        String jnlpCommand = String.format(Constants.LAUNCH_JNLP, jnlpProcess.getAgentJvmParameters(),
                jnlpProcess.getJenkinsUrl(), jnlpProcess.getUser().getUsername(), jnlpProcess.getSecret());
        return psService
                .executePsCommand(
                        String.format(Constants.START_PROCESS_RUN_AS, jnlpProcess.getUser().getUsername(),
                                jnlpProcess.getUser().getPassword(), jnlpProcess.getUser().getUsername(), jnlpCommand),
                        false);
    }

    @GetMapping("/workdir/check")
    public ExecutionResult checkWorkdirExist(@RequestParam String username) {
        return psService.executePsCommand(String.format(Constants.CHECK_WORKDIR_EXIST, username), false);
    }

    @PostMapping("/user/process/stop")
    public ExecutionResult stopUserProcess(@RequestParam String username) {
        return psService.executePsCommand(String.format(Constants.STOP_USER_PROCESS, username), false);
    }

    @GetMapping("/users/list")
    public ExecutionResult listUsers() {
        return psService.executePsCommand(Constants.LIST_USERS, false);
    }

}
