package org.carecode.middleware.mainapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        System.out.println("startProcess");
        System.out.println("processPath = " + processPath);
        Process process = createProcess(processPath);
        if (process != null) {
            processes.add(process);
        }
    }

    private Process createProcess(String processPath) {
        try {
            // Log the command to be executed
            System.out.println("Attempting to start process: java -jar " + processPath);

            // Create the process builder with "java -jar <path_to_jar>"
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", processPath);

            // Redirect error and output streams to the console for visibility
            processBuilder.redirectErrorStream(true);

            // Start the process and capture it
            Process process = processBuilder.start();

            // Log process info if started successfully
            if (process.isAlive()) {
                System.out.println("Process started successfully: " + processPath);
            } else {
                System.out.println("Process failed to start: " + processPath);
            }

            // Attach a new thread to read the output of the process
            new Thread(() -> {
                try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("Process Output: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            return process;
        } catch (Exception e) {
            System.out.println("Failed to start process at path: " + processPath);
            e.printStackTrace();
            return null;
        }
    }

}
