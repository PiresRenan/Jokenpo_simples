package com.jokenpo_simples.client.view;

import com.jokenpo_simples.client.controller.ClientController;
import com.jokenpo_simples.client.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JFrame {
    private ClientController clientController;
    private Player player1;
    private Player player2;
    private JComboBox<String> player1Move;
    private JComboBox<String> player2Move;
    private JTextArea resultArea;

    public GameUI(ClientController clientController) {
        this.clientController = clientController;
        player1 = new Player("Player1");
        player2 = new Player("Player2");

        setTitle("Jokenpo Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = getContentPane();
        container.setLayout(new GridLayout(5, 1));

        // Player 1 move
        JPanel player1Panel = new JPanel();
        player1Panel.add(new JLabel("Player 1 Move:"));
        player1Move = new JComboBox<>(new String[]{"Rock", "Paper", "Scissors"});
        player1Panel.add(player1Move);
        container.add(player1Panel);

        // Player 2 move
        JPanel player2Panel = new JPanel();
        player2Panel.add(new JLabel("Player 2 Move:"));
        player2Move = new JComboBox<>(new String[]{"Rock", "Paper", "Scissors"});
        player2Panel.add(player2Move);
        container.add(player2Panel);

        // Play button
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new PlayButtonListener());
        container.add(playButton);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        container.add(new JScrollPane(resultArea));

        // Statistics button
        JButton statsButton = new JButton("Show Player Statistics");
        statsButton.addActionListener(new StatsButtonListener());
        container.add(statsButton);

        setVisible(true);
    }

    private class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String player1MoveStr = (String) player1Move.getSelectedItem();
            String player2MoveStr = (String) player2Move.getSelectedItem();
            String result = clientController.playGame(player1MoveStr, player2MoveStr);
            resultArea.setText(result);
        }
    }

    private class StatsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Player player1Stats = clientController.getPlayerStatistics(player1.getId());
            Player player2Stats = clientController.getPlayerStatistics(player2.getId());
            resultArea.setText("Player 1 Stats: " + player1Stats + "\nPlayer 2 Stats: " + player2Stats);
        }
    }

    public static void main(String[] args) {
        ClientController clientController = new ClientController("localhost", 12345);
        new GameUI(clientController);
    }
}