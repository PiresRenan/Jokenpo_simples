package com.jokenpo_simples.server.controller;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class Servidor {
    private static final int PORTA = 12345;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o endereço IP do servidor (ou deixe em branco para usar todos): ");
        String enderecoIP = scanner.nextLine();

        try (ServerSocket serverSocket = new ServerSocket(PORTA, 0, enderecoIP.isEmpty() ? null : InetAddress.getByName(enderecoIP))) {
            System.out.println("Servidor Jokenpo iniciado na porta " + PORTA + (enderecoIP.isEmpty() ? "" : ", IP: " + enderecoIP));

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

