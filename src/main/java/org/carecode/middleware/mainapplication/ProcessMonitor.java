package org.carecode.middleware.mainapplication;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Dr M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class ProcessMonitor {

    private MiddlewareManager middlewareManager;
    private UIManager uiManager;
    private ScheduledExecutorService monitorScheduler;

    // Monitoring interval in seconds
    private static final int MONITOR_INTERVAL = 5;

    public ProcessMonitor(MiddlewareManager middlewareManager, UIManager uiManager) {
        this.middlewareManager = middlewareManager;
        this.uiManager = uiManager;
        this.monitorScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    // Starts the periodic monitoring task
    public void startMonitoring() {
        monitorScheduler.scheduleAtFixedRate(this::monitorProcesses, 0, MONITOR_INTERVAL, TimeUnit.SECONDS);
    }

    // Stops the monitoring task
    public void stopMonitoring() {
        if (monitorScheduler != null && !monitorScheduler.isShutdown()) {
            monitorScheduler.shutdown();
        }
    }

    // Checks the status of each process and updates the UI
    private void monitorProcesses() {
        for (int i = 0; i < Settings.getInstance().getProcessPaths().length; i++) {
            boolean isRunning = middlewareManager.isProcessRunning(i);

            // Update the UI with the current status
            MiddlewareStatus status = isRunning ? MiddlewareStatus.RUNNING : MiddlewareStatus.STOPPED;
            uiManager.updateStatusDisplay(i, status);

            // Log the status if it's changed
            Logger.getInstance().logProcessStatus(i, status);

            // Attempt to restart the process if it's not running
            if (!isRunning) {
                middlewareManager.restartProcess(i);
                Logger.getInstance().log("Attempted restart for Middleware " + (i + 1));
            }
        }
    }
}
