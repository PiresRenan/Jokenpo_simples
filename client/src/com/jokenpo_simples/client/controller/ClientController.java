package com.jokenpo_simples.client.controller;

import com.jokenpo_simples.client.model.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class ClientController {
    private String serverAddress;
    private int serverPort;

    public ClientController(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public String playGame(String player1Move, String player2Move) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("Jogar " + player1Move + " " + player2Move);
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro na comunicação com o servidor.";
        }
    }

    public Player getPlayerStatistics(String playerId) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("STATS " + playerId);
            String response = in.readLine();
            // Supondo que o servidor retorne os dados do jogador em formato JSON ou CSV
            // Você precisa analisar o formato e criar um objeto Player a partir dele
            return parsePlayer(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Player parsePlayer(String response) {
        // Implemente a lógica para converter a resposta do servidor em um objeto Player
        return new Player("Placeholder"); // Substitua pelo verdadeiro parsing
    }
}