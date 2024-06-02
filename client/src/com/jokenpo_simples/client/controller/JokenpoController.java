package com.jokenpo_simples.client.controller;

import com.jokenpo_simples.client.view.JokenpoView;
import com.jokenpo_simples.client.model.Jogada;
import com.jokenpo_simples.client.model.Jogador;
import com.jokenpo_simples.client.model.Resultado;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class JokenpoController {

    private JokenpoView view;
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private Jogador jogador;
    private List<String> historicoPartidas = new ArrayList<>();

    public JokenpoController(JokenpoView view) {
        this.view = view;
    }

    public void iniciar() {
        String endereco = view.obterEnderecoServidor();
        int porta = view.obterPortaServidor();
        String nome = view.obterNomeJogador();

        try {
            socket = new Socket(endereco, porta);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);

            saida.println("CRIAR_JOGADOR:" + nome);
            String resposta = entrada.readLine();
            if (resposta.startsWith("JOGADOR_CRIADO:")) {
                int idJogador = Integer.parseInt(resposta.split(":")[1]);
                jogador = new Jogador(idJogador, nome, 0, 0, 0);
                view.exibirMensagem("Jogador criado com sucesso! ID: " + idJogador);
                mostrarMenu();
            } else {
                view.exibirMensagem("Erro ao criar jogador: " + resposta);
            }
        } catch (IOException e) {
            view.exibirMensagem("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }

    private void mostrarMenu() {
        int opcao;
        do {
            opcao = view.mostrarMenu();
            switch (opcao) {
                case 1:
                    jogarContraCPU();
                    break;
                case 2:
                    jogarContraJogador();
                    break;
                case 3:
                    verEstatisticas();
                    break;
                case 4:
                    view.exibirMensagem("Saindo...");
                    break;
                default:
                    view.exibirMensagem("Opção inválida!");
            }
        } while (opcao != 4);
    }

    private void jogarContraCPU() {
        Jogada jogada = view.obterJogada();
        saida.println("JOGAR_CPU:" + jogada);
        try {
            String resposta = entrada.readLine();
            String[] answer = resposta.split(":");
            String[] partes = answer[1].split(",");

            if (answer[0].equals("RESULTADO_CPU") && partes.length == 2) {
                Resultado resultado = Resultado.valueOf(partes[0]);
                Jogada jogadaCPU = Jogada.valueOf(partes[1]);
                historicoPartidas.add("Contra CPU: Você jogou " + jogada + ", CPU jogou " + jogadaCPU + ". Resultado: " + resultado);
                view.exibirMensagem("Você jogou " + jogada + ", CPU jogou " + jogadaCPU + ". Resultado: " + resultado);
            } else {
                view.exibirMensagem("Erro ao processar resposta do servidor: " + resposta);
            }
        } catch (IOException e) {
            view.exibirMensagem("Erro ao receber resposta do servidor: " + e.getMessage());
        }
    }

    private void jogarContraJogador() {
        Jogada jogada = view.obterJogada();
        saida.println("JOGAR_JOGADOR:" + jogada);
        try {
            String resposta = entrada.readLine();
            if (resposta.equals("AGUARDANDO_OPONENTE")) {
                view.exibirMensagem("Aguardando outro jogador...");
                resposta = entrada.readLine();
            }

            String[] answer = resposta.split(":");
            String[] partes = answer[1].split(",");

            if (answer[0].equals("RESULTADO_JOGADOR") && partes.length == 3) {
                Resultado resultado = Resultado.valueOf(partes[0]);
                Jogada jogadaOponente = Jogada.valueOf(partes[1]);
                Jogada jogadaJogador = Jogada.valueOf(partes[2]);
                historicoPartidas.add("Contra Jogador: Você jogou " + jogadaJogador + ", Oponente jogou " + jogadaOponente + ". Resultado: " + resultado);
                view.exibirMensagem("Você jogou " + jogadaJogador + ", Oponente jogou " + jogadaOponente + ". Resultado: " + resultado);
            } else {
                view.exibirMensagem("Erro ao processar resposta do servidor: " + resposta);
            }
        } catch (IOException e) {
            view.exibirMensagem("Erro ao receber resposta do servidor: " + e.getMessage());
        }
    }

    private void verEstatisticas() {
        saida.println("VER_ESTATISTICAS");
        try {
            String resposta = entrada.readLine();
            if (resposta.startsWith("Vitorias:")) {
                view.exibirMensagem("Estatísticas: " + resposta);
            } else {
                view.exibirMensagem("Erro ao obter estatísticas: " + resposta);
            }
        } catch (IOException e) {
            view.exibirMensagem("Erro ao receber resposta do servidor: " + e.getMessage());
        }
    }
}
