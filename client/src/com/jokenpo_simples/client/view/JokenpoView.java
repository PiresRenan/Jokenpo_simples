package com.jokenpo_simples.client.view;

import com.jokenpo_simples.client.model.Jogada;

import java.util.Scanner;

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
        scanner.nextLine();
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
        scanner.nextLine(); // Consumir a quebra de linha

        Jogada jogada;
        switch (escolha) {
            case 1:
                jogada = Jogada.PEDRA;
                break;
            case 2:
                jogada = Jogada.PAPEL;
                break;
            case 3:
                jogada = Jogada.TESOURA;
                break;
            default:
                throw new IllegalArgumentException("Opção inválida!");
        }

        return jogada;
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

}
