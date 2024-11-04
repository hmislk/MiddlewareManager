package org.carecode.middleware.mainapplication;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Dr M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class HeartbeatChecker {

    private MiddlewareManager middlewareManager;
    private UIManager uiManager;
    private ScheduledExecutorService heartbeatScheduler;

    // Heartbeat check interval in seconds
    private static final int HEARTBEAT_INTERVAL = 10;

    public HeartbeatChecker(MiddlewareManager middlewareManager, UIManager uiManager) {
        this.middlewareManager = middlewareManager;
        this.uiManager = uiManager;
        this.heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    // Starts the periodic heartbeat check
    public void startHeartbeatCheck() {
        heartbeatScheduler.scheduleAtFixedRate(this::checkHeartbeats, 0, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

    // Stops the heartbeat check
    public void stopHeartbeatCheck() {
        if (heartbeatScheduler != null && !heartbeatScheduler.isShutdown()) {
            heartbeatScheduler.shutdown();
        }
    }

    // Checks the heartbeat of each process and attempts a restart if non-responsive
    private void checkHeartbeats() {
        for (int i = 0; i < Settings.getInstance().getProcessPaths().length; i++) {
            boolean isAlive = checkHeartbeat(i);

            // Update the UI with the current heartbeat status
            MiddlewareStatus status = isAlive ? MiddlewareStatus.RUNNING : MiddlewareStatus.STOPPED;
            uiManager.updateStatusDisplay(i, status);

            // Log the status
            Logger.getInstance().logProcessStatus(i, status);

            // Attempt to restart if the heartbeat fails
            if (!isAlive) {
                middlewareManager.restartProcess(i);
                Logger.getInstance().log("Attempted restart for Middleware " + (i + 1) + " due to failed heartbeat.");
            }
        }
    }

    // Mockup method for checking the heartbeat of a process
    private boolean checkHeartbeat(int processIndex) {
        // Implement specific logic here for heartbeat checking, such as sending a ping to the process
        // Returning true for now as a placeholder
        return middlewareManager.isProcessRunning(processIndex); // or implement actual heartbeat check
    }
}
