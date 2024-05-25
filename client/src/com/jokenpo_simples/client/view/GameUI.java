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

    public GameUI(ClientController clientController) {
        this.clientController = clientController;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Jokenpo Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JPanel loginPanel = new JPanel();
        JTextField playerNameField = new JTextField(15);
        JButton startGameButton = new JButton("Start Game");
        JButton joinGameButton = new JButton("Join Game");
        loginPanel.add(new JLabel("Player Name:"));
        loginPanel.add(playerNameField);
        loginPanel.add(startGameButton);
        loginPanel.add(joinGameButton);
        add(loginPanel);

        JPanel gamePanel = new JPanel();
        gamePanel.setVisible(false);
        JButton rockButton = new JButton("Rock");
        JButton paperButton = new JButton("Paper");
        JButton scissorsButton = new JButton("Scissors");
        JLabel resultLabel = new JLabel("");
        gamePanel.add(rockButton);
        gamePanel.add(paperButton);
        gamePanel.add(scissorsButton);
        gamePanel.add(resultLabel);
        add(gamePanel);

        JPanel statsPanel = new JPanel();
        statsPanel.setVisible(false);
        JTextArea statsArea = new JTextArea(10, 30);
        JButton showStatsButton = new JButton("Show Stats");
        statsPanel.add(new JScrollPane(statsArea));
        statsPanel.add(showStatsButton);
        add(statsPanel);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameField.getText().trim();
                if (!playerName.isEmpty()) {
                    try {
                        String response = clientController.startGame(playerName, "Player vs Player");
                        JOptionPane.showMessageDialog(GameUI.this, response);
                        loginPanel.setVisible(false);
                        gamePanel.setVisible(true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameField.getText().trim();
                if (!playerName.isEmpty()) {
                    try {
                        String response = clientController.joinOrCreateGame(playerName);
                        JOptionPane.showMessageDialog(GameUI.this, response);
                        loginPanel.setVisible(false);
                        gamePanel.setVisible(true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        ActionListener moveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String move = e.getActionCommand();
                try {
                    String result = clientController.playMove(move);
                    resultLabel.setText(result);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        rockButton.addActionListener(moveListener);
        paperButton.addActionListener(moveListener);
        scissorsButton.addActionListener(moveListener);

        showStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameField.getText().trim();
                if (!playerName.isEmpty()) {
                    try {
                        String stats = clientController.getStats(playerName);
                        statsArea.setText(stats);
                        statsPanel.setVisible(true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
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
