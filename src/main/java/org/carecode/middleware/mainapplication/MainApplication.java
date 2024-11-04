package org.carecode.middleware.mainapplication;

/**
 *
 * @author Dr M H B Ariyaratne, buddhika.ari@gmail.com
 * 
 */
public class MainApplication {

    // Reference to the UI and Middleware Managers
    private UIManager uiManager;
    private MiddlewareManager middlewareManager;

    public static void main(String[] args) {
        MainApplication app = new MainApplication();
        app.initialize();
    }

    // Initializes the application components
    private void initialize() {
        // Initialize the Middleware Manager, responsible for managing the processes
        middlewareManager = new MiddlewareManager();

        // Initialize the UI Manager, responsible for setting up the UI
        uiManager = new UIManager(middlewareManager);

        // Set up the user interface
        uiManager.initializeUI();

        // Auto-start processes if configured
        if (Settings.getInstance().isAutoStartEnabled()) {
            middlewareManager.startAllProcesses();
        }
    }
}
