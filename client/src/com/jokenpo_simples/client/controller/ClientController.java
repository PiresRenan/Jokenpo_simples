package com.jokenpo_simples.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientController(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String startGame(String playerName, String gameMode) throws IOException {
        out.println("START_GAME " + playerName + " " + gameMode);
        return in.readLine();
    }

    public String joinOrCreateGame(String playerName) throws IOException {
        out.println("JOIN_GAME " + playerName);
        return in.readLine();
    }

    public String playMove(String move) throws IOException {
        out.println("PLAY_MOVE " + move);
        return in.readLine();
    }

    public String getStats(String playerName) throws IOException {
        out.println("SHOW_STATS " + playerName);
        StringBuilder stats = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            stats.append(line).append("\n");
        }
        return stats.toString();
    }
}
