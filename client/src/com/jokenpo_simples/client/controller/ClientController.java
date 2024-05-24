package com.jokenpo_simples.client.controller;

import com.jokenpo_simples.client.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    private Socket socket;
    private Scanner scanner;

    public ClientController(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.scanner = new Scanner(System.in);
    }

    public void startClient() {}
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