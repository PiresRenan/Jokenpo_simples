package com.jokenpo_simples.server.controller;

import com.jokenpo_simples.server.model.PLayer;
import com.jokenpo_simples.server.service.GameService;

import java.util.Scanner;

public class GameController {
    private GameService gameService = new GameService();
    private Scanner scanner = new Scanner(System.in);

    public void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado na porta 12345.");
            while(true){
                Socket clientSocket = new serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void ClientHandler() implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket){
            this.clientSocket = clientSocket;
        }

        @Override
        public void run(){
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
