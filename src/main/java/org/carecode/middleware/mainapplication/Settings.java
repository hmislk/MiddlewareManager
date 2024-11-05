package org.carecode.middleware.mainapplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Dr M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class Settings {

    private static Settings instance;
    private Properties properties;

    private boolean autoStart;
    private String[] processPaths;

    private static final String CONFIG_FILE = "D:\\ccmw\\settings\\mm\\config.properties";

    // Private constructor to enforce singleton pattern
    private Settings() {
        properties = new Properties();
        loadSettings();
    }

    // Singleton instance accessor
    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    // Loads settings from a configuration file
    private void loadSettings() {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);

            // Load the auto-start setting
            autoStart = Boolean.parseBoolean(properties.getProperty("autoStart", "false"));

            // Load the process paths
            String paths = properties.getProperty("processPaths", "");
            processPaths = paths.isEmpty() ? new String[0] : paths.split(";");
        } catch (IOException e) {
            System.err.println("Error loading settings: " + e.getMessage());
            autoStart = false;
            processPaths = new String[0];
        }
    }

    // Saves settings to a configuration file
    public void saveSettings() {
        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.setProperty("autoStart", Boolean.toString(autoStart));
            properties.setProperty("processPaths", String.join(";", processPaths));
            properties.store(output, null);
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    // Getters and setters for autoStart and processPaths
    public boolean isAutoStartEnabled() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }

    public String[] getProcessPaths() {
        String paths = properties.getProperty("processPaths", "");
        return paths.isEmpty() ? new String[0] : paths.split(",");
    }

    public void setProcessPaths(String[] processPaths) {
        this.processPaths = processPaths;
    }
}
