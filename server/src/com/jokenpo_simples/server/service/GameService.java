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
        return game.getResult();
    }

    public Player getPlayerStatistics(String playerId){

    }

}
