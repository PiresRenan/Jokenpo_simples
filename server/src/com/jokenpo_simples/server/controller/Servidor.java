package com.jokenpo_simples.server.controller;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PORTA = 12345;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor Jokenpo iniciado na porta " + PORTA);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

                pool.execute(new GerenciadorPartidas(clienteSocket));
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}

