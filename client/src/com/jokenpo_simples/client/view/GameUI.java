package com.jokenpo_simples.client.view;

import com.jokenpo_simples.client.controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class GameUI extends JFrame {
    private ClientController clientController;
    private String playerName;

    public GameUI(ClientController clientController) {
        this.clientController = clientController;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Jokenpo Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel nameLabel = new JLabel("Enter your name:");
        JTextField nameField = new JTextField();
        JButton setNameButton = new JButton("Set Name");
        setNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameField.getText();
                if (playerName != null && !playerName.isEmpty()) {
                    clientController.setPlayerName(playerName);
                    nameField.setEnabled(false);
                    setNameButton.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty!");
                }
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(setNameButton);

        JButton playVsCPUButton = new JButton("Play vs CPU");
        playVsCPUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playVsCPU();
            }
        });
        panel.add(playVsCPUButton);

        JButton playVsPlayerButton = new JButton("Play vs Player");
        playVsPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playVsPlayer();
            }
        });
        panel.add(playVsPlayerButton);

        JButton statsButton = new JButton("View Statistics");
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStatistics();
            }
        });
        panel.add(statsButton);

        add(panel);
    }

    private void playVsCPU() {
        if (playerName == null || playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must set your name first!");
            return;
        }

        String[] options = {"Rock", "Paper", "Scissors"};
        String playerMove = (String) JOptionPane.showInputDialog(this, "Choose your move:", "Play vs CPU", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (playerMove != null) {
            try {
                String result = clientController.playVsCPU(playerMove);
                JOptionPane.showMessageDialog(this, result);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void playVsPlayer() {
        if (playerName == null || playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must set your name first!");
            return;
        }

        String[] options = {"Rock", "Paper", "Scissors"};
        String playerMove = (String) JOptionPane.showInputDialog(this, "Choose your move:", "Play vs Player", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (playerMove != null) {
            try {
                String result = clientController.playVsPlayer(playerMove);
                JOptionPane.showMessageDialog(this, result);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void viewStatistics() {
        if (playerName == null || playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must set your name first!");
            return;
        }

        try {
            String stats = clientController.getStats(playerName);
            JOptionPane.showMessageDialog(this, stats);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            ClientController clientController = new ClientController(socket);
            SwingUtilities.invokeLater(() -> new GameUI(clientController).setVisible(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
