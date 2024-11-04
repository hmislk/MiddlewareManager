package org.carecode.middleware.mainapplication;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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

    // Initializes the main UI components
    public void initializeUI() {
        frame = new JFrame("Middleware Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(Settings.getInstance().getProcessPaths().length, 4));

        initializeComponents();
        setActionListeners();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Initializes the UI components (status labels and control buttons)
    private void initializeComponents() {
        int processCount = Settings.getInstance().getProcessPaths().length;
        processStatusLabels = new JLabel[processCount];
        startButtons = new JButton[processCount];
        stopButtons = new JButton[processCount];
        restartButtons = new JButton[processCount];

        for (int i = 0; i < processCount; i++) {
            processStatusLabels[i] = new JLabel("Status: UNKNOWN");
            startButtons[i] = new JButton("Start");
            stopButtons[i] = new JButton("Stop");
            restartButtons[i] = new JButton("Restart");

            mainPanel.add(new JLabel("Middleware " + (i + 1)));
            mainPanel.add(processStatusLabels[i]);
            mainPanel.add(startButtons[i]);
            mainPanel.add(stopButtons[i]);
            mainPanel.add(restartButtons[i]);
        }
    }

    // Sets action listeners for each button
    private void setActionListeners() {
        for (int i = 0; i < startButtons.length; i++) {
            final int index = i;

            startButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    middlewareManager.startProcess(Settings.getInstance().getProcessPaths()[index]);
                    updateStatusDisplay(index, MiddlewareStatus.RUNNING);
                }
            });

            stopButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    middlewareManager.stopAllProcesses(); // or use specific process stop if needed
                    updateStatusDisplay(index, MiddlewareStatus.STOPPED);
                }
            });

            restartButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    middlewareManager.restartProcess(index);
                    updateStatusDisplay(index, MiddlewareStatus.RUNNING);
                }
            });
        }
    }

    // Updates the status label of a specific middleware process
    public void updateStatusDisplay(int processIndex, MiddlewareStatus status) {
        processStatusLabels[processIndex].setText("Status: " + status.name());
    }
}