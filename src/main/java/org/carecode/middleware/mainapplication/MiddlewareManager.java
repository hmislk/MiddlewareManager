package org.carecode.middleware.mainapplication;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dr M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class MiddlewareManager {

    // List to hold the processes for each middleware application
    private List<Process> processes;

    public MiddlewareManager() {
        processes = new ArrayList<>();
    }

    // Starts all middleware applications
    public void startAllProcesses() {
        // Iterate over each process path defined in Settings and start each process
        for (String processPath : Settings.getInstance().getProcessPaths()) {
            startProcess(processPath);
        }
    }

    // Stops all middleware applications
    public void stopAllProcesses() {
        for (Process process : processes) {
            if (process.isAlive()) {
                process.destroy();
            }
        }
        processes.clear();
    }

    // Restarts a specific process by index
    public void restartProcess(int processIndex) {
        if (processIndex >= 0 && processIndex < processes.size()) {
            Process process = processes.get(processIndex);
            if (process.isAlive()) {
                process.destroy();
            }
            processes.set(processIndex, createProcess(Settings.getInstance().getProcessPaths()[processIndex]));
        }
    }

    // Checks if a specific process is running
    public boolean isProcessRunning(int processIndex) {
        if (processIndex >= 0 && processIndex < processes.size()) {
            Process process = processes.get(processIndex);
            return process.isAlive();
        }
        return false;
    }

    // Starts a specific middleware application by path and adds it to the list
    public void startProcess(String processPath) {
        Process process = createProcess(processPath);
        if (process != null) {
            processes.add(process);
        }
    }

    // Helper method to create a process from a given path
    private Process createProcess(String processPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(processPath);
            return processBuilder.start();
        } catch (Exception e) {
            Logger.getInstance().logError("Failed to start process at path: " + processPath);
            return null;
        }
    }
}