package com.jokenpo_simples.server.service;

import com.jokenpo_simples.server.model.Game;
import com.jokenpo_simples.server.model.Player;

import java.util.HashMap;
import java.util.Map;

public class GameService {

    private Map<String, Game> games = new HashMap<>();

    public Game createGame(Player player1, Player player2){
        Game game = new Game(player1, player2);
        games.put(game.getGameId(), game);
        return game;
    }

    public String playGame(String gameId, String Player1Move, String Player2Move){
        Game game = games.get(gameId);
        if (player1Move.equals(player2Move)) {
            game.setResult("Tie");
            game.getPlayer1().incrementTies();
            game.getPlayer2().incrementTies();
        } else if ( (player1Move.equals("Rock") && player2Move.equals("Scissors")) ||
                (player1Move.equals("Scissors") && player2Move.equals("Paper")) ||
                (player1Move.equals("Paper") && player2Move.equals("Rock")) ) {
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

    public Player getPlayerStatistics(String playerId){

    }

}
