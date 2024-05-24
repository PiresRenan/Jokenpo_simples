package com.jokenpo_simples.client.controller;

import com.jokenpo_simples.client.model.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class ClientController {
    private Socket socket;
    private Scanner scanner;

    public ClientController(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.scanner = new Scanner(System.in);
    }

    public void startClient() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Logic to interact with the server
            // ...

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleInput() {}
    public static void main(String[] args) {
        try {
            ClientController client = new ClientController("localhost", 12345);
            client.startClient();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}