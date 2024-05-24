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
    private String playerName;

    public ClientController(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String playVsCPU(String move) throws IOException {
        out.println("PLAY_VS_CPU:" + playerName + ":" + move);
        return in.readLine();
    }

    public String playVsPlayer(String move) throws IOException {
        out.println("PLAY_VS_PLAYER:" + playerName + ":" + move);
        return in.readLine();
    }

    public String getStats(String playerName) throws IOException {
        out.println("GET_STATS:" + playerName);
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
