package org.carecode.middleware.mainapplication;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Dr M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class Logger {

    private static Logger instance;
    private static final String LOG_FILE_PATH = "application.log";
    private PrintWriter writer;

    // Private constructor for singleton pattern
    private Logger() {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true), true);
        } catch (IOException e) {
            System.err.println("Error initializing logger: " + e.getMessage());
        }
    }

    // Singleton instance accessor
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Logs a general message with a timestamp
    public void log(String message) {
        logMessage("INFO", message);
    }

    // Logs an error message with a timestamp
    public void logError(String message) {
        logMessage("ERROR", message);
    }

    // Logs the status of a specific middleware process
    public void logProcessStatus(int processIndex, MiddlewareStatus status) {
        logMessage("STATUS", "Middleware " + (processIndex + 1) + " is " + status.name());
    }

    // Helper method to log messages with a severity level
    private void logMessage(String level, String message) {
        if (writer != null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println("[" + timestamp + "] [" + level + "] " + message);
        }
    }

    // Closes the writer when application is done logging
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}