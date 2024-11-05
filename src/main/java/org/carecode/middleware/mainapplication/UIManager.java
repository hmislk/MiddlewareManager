package org.carecode.middleware.mainapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author Dr M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class UIManager {

    private MiddlewareManager middlewareManager;
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel[] processStatusLabels;
    private JButton[] startButtons;
    private JButton[] stopButtons;
    private JButton[] restartButtons;

    public UIManager(MiddlewareManager middlewareManager) {
        this.middlewareManager = middlewareManager;
    }

    public void initializeUI() {
        frame = new JFrame("Middleware Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        mainPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add space around components

        initializeComponents(gbc);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void initializeComponents(GridBagConstraints gbc) {
        int processCount = Settings.getInstance().getProcessPaths().length;
        processStatusLabels = new JLabel[processCount];
        startButtons = new JButton[processCount];
        stopButtons = new JButton[processCount];
        restartButtons = new JButton[processCount];

        for (int i = 0; i < processCount; i++) {
            String fullPath = Settings.getInstance().getProcessPaths()[i];
            String fileName = new File(fullPath).getName();

            gbc.gridx = 0;
            gbc.gridy = i;
            mainPanel.add(new JLabel(fileName), gbc);

            processStatusLabels[i] = new JLabel("Status: UNKNOWN");
            gbc.gridx = 1;
            mainPanel.add(processStatusLabels[i], gbc);

            startButtons[i] = new JButton("Start");
            startButtons[i].setPreferredSize(new Dimension(80, 25));
            gbc.gridx = 2;
            mainPanel.add(startButtons[i], gbc);

            stopButtons[i] = new JButton("Stop");
            stopButtons[i].setPreferredSize(new Dimension(80, 25));
            gbc.gridx = 3;
            mainPanel.add(stopButtons[i], gbc);

            restartButtons[i] = new JButton("Restart");
            restartButtons[i].setPreferredSize(new Dimension(80, 25));
            gbc.gridx = 4;
            mainPanel.add(restartButtons[i], gbc);

            // Setting action listeners for each button
            final int index = i; // to reference the correct process in listeners
            startButtons[i].addActionListener(e -> {
                String processPath = Settings.getInstance().getProcessPaths()[index];
                middlewareManager.startProcess(processPath);
                updateStatusDisplay(index, MiddlewareStatus.RUNNING);
            });

            stopButtons[i].addActionListener(e -> {
                middlewareManager.stopAllProcesses(); // Change this if you only want to stop the specific process
                updateStatusDisplay(index, MiddlewareStatus.STOPPED);
            });

            restartButtons[i].addActionListener(e -> {
                middlewareManager.restartProcess(index);
                updateStatusDisplay(index, MiddlewareStatus.RUNNING);
            });
        }
    }

    private void setActionListeners() {
        // Set up action listeners as before (not shown here for brevity)
    }

    public void updateStatusDisplay(int processIndex, MiddlewareStatus status) {
        processStatusLabels[processIndex].setText("Status: " + status.name());
    }
}
