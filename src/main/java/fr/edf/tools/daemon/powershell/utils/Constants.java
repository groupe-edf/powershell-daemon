package fr.edf.tools.daemon.powershell.utils;

public final class Constants {

    private Constants() {
        // final class
    }

    /** An unexpected exception occured when performing the command %s */
    public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected exception occured when performing the command %s";

    /** Windows */
    public static final String WINDOWS = "Windows";

    /** os.name */
    public static final String OS_PROPERTY = "os.name";

    /** token.txt */
    public static final String TOKEN_FILE = "token.txt";

    /** server.servlet.context-path */
    public static final String CONTEXT_PATH_PROPERTY_NAME = "server.servlet.context-path";

    /** /powershell-daemon */
    public static final String CONTEXT_PATH = "/powershell-daemon";

    /** powershell-daemon.http.auth-token-header-name */
    public static final String TOKEN_HEADER_NAME_PROP = "powershell-daemon.http.auth-token-header-name";

    /** token */
    public static final String TOKEN_HEADER_NAME = "Token";

    /** powershell-daemon.http.auth-token */
    public static final String TOKEN_HEADER_VALUE_PROP = "powershell-daemon.http.auth-token";

    /** Remote Management Users */
    public static final String REMOTE_MANAGEMENT_USERS_GROUP = "Remote Management Users";

    // PowerShell Standard Commands

    /** whoami */
    public static final String WHOAMI = "whoami";

    /**
     * New-LocalUser \"%s\" -Password \$(\"%s\" | ConvertTo-SecureString
     * -AsPlainText -Force) -FullName \"%s\" -Description \"User automatically
     * created by jenkins\"
     */
    public static final String CREATE_USER = "New-LocalUser \"%s\" -Password $(\"%s\" | ConvertTo-SecureString -AsPlainText -Force) -FullName \"%s\" -Description \"User automatically created by jenkins\"";

    /** Add-LocalGroupMember -Group \"%s\" -Member \"%s\ */
    public static final String ADD_USER_TO_GROUP = "Add-LocalGroupMember -Group \"%s\" -Member \"%s\"";

    /**
     * Get-Process -IncludeUserName | Where-Object {\$_.UserName -eq
     * \"\$env:COMPUTERNAME\\%s\"} | Stop-Process -Force
     */
    public static final String STOP_USER_PROCESS = "Get-Process -IncludeUserName | Where-Object {$_.UserName -eq \"$env:COMPUTERNAME\\%s\"} | Stop-Process -Force";

    /** Remove-LocalUser -Name \"%s\" -Verbose */
    public static final String DELETE_USER = "Remove-LocalUser -Name \"%s\" -Verbose";

    /** C:\\Users\\%s\\ */
    public static final String WORKDIR_PATTERN = "C:\\Users\\%s\\";

    /** New-Item -Path %s -ItemType 'directory' -Force */
    public static final String CREATE_DIR = "New-Item -Path %s -ItemType 'directory' -Force";

    /**
     * \$acl = Get-Acl \"C:\\users\\%s\"
     * \$acl.SetAccessRuleProtection(\$true,\$true) \$acl | Set-Acl
     * \"C:\\users\\%s\"
     */
    public static final String DISABLE_INHERITED_WORKDIR = "$acl = Get-Acl \"C:\\users\\%s\";"
            + "$acl.SetAccessRuleProtection($true,$true);" + "$acl | Set-Acl \"C:\\users\\%s\"";

    /**
     * \$acl = Get-Acl \"C:\\users\\%s\" \$acl.Access | Where-Object
     * {\$_.IdentityReference -notlike \"*Administrators*\" -and
     * \$_.IdentityReference -notlike \"*SYSTEM*\"} | ForEach-Object -Process
     * {\$acl.RemoveAccessRule(\$_)} \$aclDef = \"\$env:COMPUTERNAME\\%s\",
     * \"FullControl\", \"ContainerInherit,ObjectInherit\", \"None\", \"Allow\"
     * \$aclRule = New-Object System.Security.AccessControl.FileSystemAccessRule
     * \$aclDef \$acl.SetAccessRule(\$aclRule)
     * \$acl.SetOwner([System.Security.Principal.NTAccount]\"NT AUTHORITY\\SYSTEM\")
     * \$acl | Set-Acl \"C:\\users\\%s\"
     */
    public static final String GRANT_ACCESS_WORKDIR = "$acl = Get-Acl \"C:\\users\\%s\";"
            + "$acl.Access | Where-Object {$_.IdentityReference -notlike \"*Administrators*\" -and $_.IdentityReference -notlike \"*SYSTEM*\"} | ForEach-Object -Process {$acl.RemoveAccessRule($_)};"
            + "$aclDef = \"$env:COMPUTERNAME\\%s\", \"FullControl\", \"ContainerInherit,ObjectInherit\", \"None\", \"Allow\";"
            + "$aclRule = New-Object System.Security.AccessControl.FileSystemAccessRule $aclDef;"
            + "$acl.SetAccessRule($aclRule);"
            + "$acl.SetOwner([System.Security.Principal.NTAccount]\"NT AUTHORITY\\SYSTEM\");"
            + "$acl | Set-Acl \"C:\\users\\%s\"";

    /** Remove-Item 'C:\\Users\\%s' -Force -Recurse */
    public static final String REMOVE_WORKDIR = "Remove-Item 'C:\\Users\\%s' -Force -Recurse";

    /** Check-Path -PathType Container 'C:\\Users\\%s' */
    public static final String CHECK_WORKDIR_EXIST = "Test-Path -PathType Container 'C:\\Users\\%s'";

    /** Invoke-RestMethod -Uri %s -OutFile remoting.jar */
    public static final String GET_REMOTING_JAR = "Invoke-RestMethod -Uri %s -OutFile remoting.jar";

    /** (Get-LocalUser | Where-Object {\$_.Name -eq %s}).name */
    public static final String CHECK_USER_EXIST = "(Get-LocalUser | Where-Object {$_.Name -eq '%s'}).name";

    /** java -jar remoting.jar -jnlpUrl %scomputer/%s/slave-agent.jnlp -secret %s */
    public static final String LAUNCH_JNLP = "java -jar remoting.jar -jnlpUrl %scomputer/%s/slave-agent.jnlp -secret %s";

    /** (Get-LocalUser | Where-Object {\$_.Name -match \"windows-*\"}).name */
    public static final String LIST_USERS = "(Get-LocalUser | Where-Object {$_.Name -match \"windows-*\"}).name";

    /** \\r?\\n|\\r */
    public static final String REGEX_NEW_LINE = "\\r?\\n|\\r";
}
