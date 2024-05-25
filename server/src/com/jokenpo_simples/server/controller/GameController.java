package com.jokenpo_simples.server.controller;

import com.jokenpo_simples.server.database.DatabaseConnection;
import com.jokenpo_simples.server.service.GameService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameController {
    private static GameService gameService = new GameService();

    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            DatabaseConnection.initializeDatabase();
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String[] tokens = inputLine.split(" ");
                    String command = tokens[0];

                    switch (command) {
                        case "START_GAME":
                            String playerName = tokens[1];
                            String gameMode = tokens[2];
                            String gameId = gameService.createGame(playerName, gameMode, clientSocket);
                            out.println("Game started with ID: " + gameId);
                            break;

                        case "JOIN_GAME":
                            String joinPlayerName = tokens[1];
                            String joinGameId = gameService.joinOrCreateGame(joinPlayerName, clientSocket);
                            out.println("Joined or created game with ID: " + joinGameId);
                            break;

                        case "PLAY_MOVE":
                            String move = tokens[1];
                            String result = gameService.playMove(clientSocket, move);
                            out.println(result);
                            break;

                        case "SHOW_STATS":
                            String statsPlayerName = tokens[1];
                            String stats = gameService.getStats(statsPlayerName);
                            out.println(stats);
                            break;

                        default:
                            out.println("Unknown command: " + command);
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        startServer();
    }
}
