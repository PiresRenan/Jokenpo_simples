package com.jokenpo_simples.server.controller;

import com.jokenpo_simples.server.model.Jogada;
import com.jokenpo_simples.server.model.Jogador;
import com.jokenpo_simples.server.model.Resultado;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class GerenciadorPartidas implements Runnable {

    private final Socket clienteSocket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private Jogador jogador;

    public GerenciadorPartidas(Socket socket) {
        this.clienteSocket = socket;
        try {
            this.entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            this.saida = new PrintWriter(clienteSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Erro ao criar streams de E/S para o cliente: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String mensagem;
            while ((mensagem = entrada.readLine()) != null) {
                System.out.println("Mensagem recebida do cliente: " + mensagem);
                String resposta = processarMensagem(mensagem);
                saida.println(resposta);
            }
        } catch (SocketException e) {
            System.out.println("Cliente desconectado: " + clienteSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
        } finally {
            try {
                clienteSocket.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar socket do cliente: " + e.getMessage());
            }
        }
    }

    private String processarMensagem(String mensagem) {
        String[] partes = mensagem.split(":");
        String tipo = partes[0];
        String dados = partes.length > 1 ? partes[1] : "";

        try (var conn = GerenciadorBancoDados.obterConexao()) {
            switch (tipo) {
                case "CRIAR_JOGADOR":
                    this.jogador = new Jogador();
                    this.jogador.setId(GerenciadorBancoDados.criarJogador(conn, dados).getId());
                    this.jogador.setNome(dados);
                    return "JOGADOR_CRIADO:" + this.jogador.getId();
                case "JOGAR_CPU":
                    Jogada jogadaCliente = Jogada.valueOf(dados);
                    Jogada jogadaCPU = Jogada.values()[new Random().nextInt(Jogada.values().length)];
                    Resultado resultado = calcularResultado(jogadaCliente, jogadaCPU);
                    GerenciadorBancoDados.atualizarEstatisticas(conn, this.jogador.getId(), resultado);
                    GerenciadorBancoDados.adicionarPartidaAoHistorico(conn, this.jogador.getId(), Jogada.valueOf(String.valueOf(jogadaCliente)), jogadaCPU, resultado);
                    return "RESULTADO_CPU:" + resultado + "," + jogadaCPU;
                case "VER_ESTATISTICAS":
                    this.jogador = GerenciadorBancoDados.obterJogador(conn, this.jogador.getId());
                    String stats = GerenciadorBancoDados.obterEstatisticas(conn, this.jogador.getId());
                    System.out.println(stats);
                    return stats;
                default:
                    return "ERRO:Mensagem inválida";
            }
        } catch (SQLException | IllegalArgumentException e) {
            return "ERRO:" + e.getMessage();
        }
    }

    private Resultado calcularResultado(Jogada jogada1, Jogada jogada2) {
        if (jogada1 == jogada2) {
            return Resultado.EMPATE;
        } else if ((jogada1 == Jogada.PEDRA && jogada2 == Jogada.TESOURA) ||
                (jogada1 == Jogada.PAPEL && jogada2 == Jogada.PEDRA) ||
                (jogada1 == Jogada.TESOURA && jogada2 == Jogada.PAPEL)) {
            return Resultado.VITORIA;
        } else {
            return Resultado.DERROTA;
        }
    }
}