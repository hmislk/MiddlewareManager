package org.carecode.middleware.mainapplication;

/**
 *
 * @author Dr M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class Constants {

    // Path to the configuration file
    public static final String CONFIG_FILE_PATH = "config.properties";

    // Path to the log file
    public static final String LOG_FILE_PATH = "application.log";

    // Default interval for process monitoring (in seconds)
    public static final int DEFAULT_MONITOR_INTERVAL = 5;

    // Default interval for heartbeat checks (in seconds)
    public static final int DEFAULT_HEARTBEAT_INTERVAL = 10;

    // Default process names (example)
    public static final String[] PROCESS_NAMES = {
        "Middleware Server 1",
        "Middleware Server 2",
        "Middleware Server 3",
        "Middleware Client 1",
        "Middleware Client 2",
        "Middleware Client 3"
    };

    // Log message formats
    public static final String LOG_FORMAT_INFO = "[INFO] %s";
    public static final String LOG_FORMAT_ERROR = "[ERROR] %s";
    public static final String LOG_FORMAT_STATUS = "[STATUS] Middleware %d is %s";
}

