package com.jokenpo_simples.server.model;

import java.util.UUID;

public class Game {
    private String gameId;
    private Player player1;
    private Player player2;
    private String player1Move;
    private String result;

    public Game(Player player1, Player player2) {
        this.gameId = UUID.randomUUID().toString();
        this.player1 = player1;
        this.player2 = player2;
        this.player1Move = null;
        this.result = null;
    }

    public String getGameId() {
        return gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getPlayer1Move() {
        return player1Move;
    }

    public void setPlayer1Move(String player1Move) {
        this.player1Move = player1Move;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}