package com.jokenpo_simples.server.controller;

import com.jokenpo_simples.server.model.Game;
import com.jokenpo_simples.server.model.Player;
import com.jokenpo_simples.server.service.GameService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class GameController {
    private GameService gameService = new GameService();

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado!");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String input = in.readLine();
                if (input.startsWith("PLAY")) {
                    String[] parts = input.split(" ");
                    String player1Move = parts[1];
                    String player2Move = parts[2];
                    Game game = gameService.createGame(new Player("Player1"), new Player("Player2"));
                    String result = gameService.playGame(game.getGameId(), player1Move, player2Move);
                    out.println(result);
                } else if (input.startsWith("STATS")) {
                    String playerId = input.split(" ")[1];
                    Player player = gameService.getPlayerStatistics(playerId);
                    out.println(player.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new GameController().startServer();
    }
}