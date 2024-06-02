package com.jokenpo_simples.client.view;

import com.jokenpo_simples.client.model.Jogada;
import com.jokenpo_simples.client.model.Jogador;

import java.util.Scanner;
import java.util.List;

public class JokenpoView {
    private Scanner scanner;

    public JokenpoView() {
        scanner = new Scanner(System.in);
    }

    public String obterEnderecoServidor() {
        System.out.print("Digite o endereço IP do servidor: ");
        return scanner.nextLine();
    }

    public int obterPortaServidor() {
        System.out.print("Digite a porta do servidor: ");
        return scanner.nextInt();
    }

    public String obterNomeJogador() {
        System.out.print("Digite seu nome: ");
        scanner.nextLine(); // Consumir a quebra de linha
        return scanner.nextLine();
    }

    public int mostrarMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1. Jogar contra a CPU");
        System.out.println("2. Jogar contra outro jogador");
        System.out.println("3. Ver estatísticas");
        System.out.println("4. Sair");
        return scanner.nextInt();
    }

    public Jogada obterJogada() {
        System.out.println("\nEscolha sua jogada:");
        System.out.println("1. PEDRA");
        System.out.println("2. PAPEL");
        System.out.println("3. TESOURA");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        return switch (escolha) {
            case 1 -> Jogada.PEDRA;
            case 2 -> Jogada.PAPEL;
            case 3 -> Jogada.TESOURA;
            default -> throw new IllegalArgumentException("Opção inválida!");
        };
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirEstatisticas(Jogador jogador, List<String> historicoPartidas) {
        System.out.println("Estatísticas de " + jogador.getNome() + ":");
        System.out.println("Vitórias: " + jogador.getVitorias());
        System.out.println("Derrotas: " + jogador.getDerrotas());
        System.out.println("Empates: " + jogador.getEmpates());

        System.out.println("\nHistórico de Partidas:");
        if (historicoPartidas.isEmpty()) {
            System.out.println("Nenhuma partida jogada ainda.");
        } else {
            for (String partida : historicoPartidas) {
                System.out.println(partida);
            }
        }
    }
}
