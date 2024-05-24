package com.jokenpo_simples.server.model;

import java.util.UUID;

public class Game {

    private String gameID;
    private Player player1;
    private Player player2;
    private String result;

    public Game(Player player1, Player player2){
        this.gameID = UUID.randomUUID().toString();
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}