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

    @PostMapping("/user/create")
    public ExecutionResult createUser(@RequestBody User user) {
        return psService.executePsCommand(
                String.format(Constants.CREATE_USER, user.getUsername(), user.getPassword(), user.getUsername()),
                false);
    }

    @PostMapping("/user/group/add")
    public ExecutionResult addUserToGroup(@RequestParam String username, @RequestParam String groupname) {
        return psService.executePsCommand(String.format(Constants.ADD_USER_TO_GROUP, groupname, username), false);
    }

    @PostMapping("/user/delete")
    public ExecutionResult deleteUser(@RequestParam String username) {
        return psService.executePsCommand(String.format(Constants.DELETE_USER, username), false);
    }

    @PostMapping("/user/process/stop")
    public ExecutionResult stopUserProcess(@RequestParam String username) {
        return psService.executePsCommand(String.format(Constants.STOP_USER_PROCESS, username), false);
    }

    @GetMapping("/user/workdir")
    public ExecutionResult checkGroupExist(@RequestParam String username) {
        return psService.executePsCommand(String.format(Constants.CHECK_WORKDIR_EXIST, username), false);
    }

    @GetMapping("/user")
    public ExecutionResult checkUserExist(@RequestParam String username) {
        return psService.executePsCommand(String.format(Constants.CHECK_USER_EXIST, username), false);
    }

    @GetMapping("/users/list")
    public ExecutionResult listUsers() {
        return psService.executePsCommand(Constants.LIST_USERS, false);
    }

    @GetMapping("/remoting")
    public ExecutionResult getRemoting(@RequestParam String remotingUrl) {
        return psService.executePsCommand(String.format(Constants.GET_REMOTING_JAR, remotingUrl), false);
    }

    @PostMapping("/jnlp")
    public ExecutionResult runJnlp(@RequestBody JnlpProcess jnlpProcess) {
        return psService.executePsCommand(String.format(Constants.LAUNCH_JNLP, jnlpProcess.getJenkinsUrl(),
                jnlpProcess.getUsername(), jnlpProcess.getSecret()), false);
    }

}
