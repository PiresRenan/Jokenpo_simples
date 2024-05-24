package com.jokenpo_simples.server.controller;

import com.jokenpo_simples.server.model.PLayer;
import com.jokenpo_simples.server.service.GameService;

import java.util.Scanner;

public class GameController {
    private GameService gameService = new GameService();
    private Scanner scanner = new Scanner(System.in);

    public void startServer(){

    }

    private void handleInput(){

    }

    public static void main(String[] args) {
        GameController controller = new GameController();
        controller.startServer();
    }

}
