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
    public static final String TOKEN_HEADER_NAME = "token";

    /** powershell-daemon.http.auth-token */
    public static final String TOKEN_HEADER_VALUE_PROP = "powershell-daemon.http.auth-token";
}
