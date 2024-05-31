package com.jokenpo_simples.client.model;

public class Jogador {
    private int id;
    private String nome;
    private int vitorias;
    private int derrotas;
    private int empates;

    // Construtor vazio para o Gson
    public Jogador() {}

    public Jogador(int id, String nome, int vitorias, int derrotas, int empates) {
        this.id = id;
        this.nome = nome;
        this.vitorias = vitorias;
        this.derrotas = derrotas;
        this.empates = empates;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    @Override
    public String toString() {
        return "JOGADOR:" + id + "," + nome + "," + vitorias + "," + derrotas + "," + empates;
    }
}