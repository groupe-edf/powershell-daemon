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
import fr.edf.tools.daemon.powershell.model.ProcessRun;
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

    @PostMapping("/createUser")
    public ExecutionResult createUser(@RequestBody User user) {
        return psService.createUser(user);
    }

    @PostMapping("/addUserToGroup")
    public ExecutionResult addUserToGroup(@RequestParam String username, @RequestParam String groupname) {
        return psService.addUserToGroup(username, groupname);
    }

    @PostMapping("/deleteUser")
    public ExecutionResult deleteUser(@RequestParam String username) {
        return psService.deleteUser(username);
    }

    @GetMapping("/checkWorkdirExist")
    public ExecutionResult checkGroupExist(@RequestParam String username) {
        return psService.getWorkdirName(username);
    }

    @GetMapping("/checkUserExist")
    public ExecutionResult checkUserExist(@RequestParam String username) {
        return psService.getUsername(username);
    }

    @GetMapping("/listUsers")
    public ExecutionResult listUsers() {
        return psService.listUsers();
    }

    @PostMapping("/runProcess")
    public ExecutionResult runProcess(@RequestBody ProcessRun processRun) {
        return null;
    }

}
