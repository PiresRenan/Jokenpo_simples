package com.jokenpo_simples.server.model;

public class Player {

    private String id;
    private String name;
    private int wins;
    private int losses;
    private int draws;

    public Player(String name) {
        this.id = name;
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public void incrementDraws() {
        this.draws++;
    }
}