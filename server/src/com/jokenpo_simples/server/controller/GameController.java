package com.jokenpo_simples.server.controller;

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
    private Scanner scanner = new Scanner(System.in);

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started on port 12345");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Correção aqui
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String input;
                while ((input = in.readLine()) != null) {
                    // Logic to handle input from client and send responses
                    // ...
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.startServer();
    }
}