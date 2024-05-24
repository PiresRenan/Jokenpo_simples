package com.jokenpo_simples.server.service;

import com.jokenpo_simples.server.model.Game;
import com.jokenpo_simples.server.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameService {
    private Map<String, Game> games = new HashMap<>();
    private Map<String, Player> players = new HashMap<>(); // Mapa para armazenar jogadores por ID
    private Random random = new Random();

    public Game createGame(Player player1, Player player2) {
        Game game = new Game(player1, player2);
        games.put(game.getGameId(), game);
        players.put(player1.getId(), player1); // Adiciona player1 ao mapa de jogadores
        players.put(player2.getId(), player2); // Adiciona player2 ao mapa de jogadores
        return game;
    }

    public String playGame(String gameId, String player1Move, String player2Move) {
        Game game = games.get(gameId);

        if (player1Move.equals(player2Move)) {
            game.setResult("Draw");
            game.getPlayer1().incrementDraws();
            game.getPlayer2().incrementDraws();
        } else if ((player1Move.equals("Rock") && player2Move.equals("Scissors")) ||
                (player1Move.equals("Scissors") && player2Move.equals("Paper")) ||
                (player1Move.equals("Paper") && player2Move.equals("Rock"))) {
            game.setResult("Player 1 wins");
            game.getPlayer1().incrementWins();
            game.getPlayer2().incrementLosses();
        } else {
            game.setResult("Player 2 wins");
            game.getPlayer1().incrementLosses();
            game.getPlayer2().incrementWins();
        }
        return game.getResult();
    }

    public String playGameAgainstMachine(String gameId, String playerMove) {
        Game game = games.get(gameId);
        String[] moves = {"Rock", "Paper", "Scissors"};
        String machineMove = moves[random.nextInt(moves.length)];

        return playGame(gameId, playerMove, machineMove);
    }

    public Player getPlayerStatistics(String playerId) {
        Player player = players.get(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Player not found with ID: " + playerId);
        }
        return player;
    }
}
