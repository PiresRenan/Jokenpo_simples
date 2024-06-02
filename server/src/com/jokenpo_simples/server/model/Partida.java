package com.jokenpo_simples.server.model;

public class Partida {
    private int id;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogada jogadaJogador1;
    private Jogada jogadaJogador2;
    private int resultado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public Jogada getJogadaJogador1() {
        return jogadaJogador1;
    }

    public void setJogadaJogador1(Jogada jogadaJogador1) {
        this.jogadaJogador1 = jogadaJogador1;
    }

    public Jogada getJogadaJogador2() {
        return jogadaJogador2;
    }

    public void setJogadaJogador2(Jogada jogadaJogador2) {
        this.jogadaJogador2 = jogadaJogador2;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

}
