package com.jokenpo_simples.server.service;

import com.jokenpo_simples.server.database.DatabaseConnection;
import com.jokenpo_simples.server.model.Game;
import com.jokenpo_simples.server.model.Player;

import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameService {
    private Map<String, Game> games = new HashMap<>();
    private Random random = new Random();
    private Map<Socket, String> playerSockets = new HashMap<>();

    public String createGame(String playerName, String gameMode) {
        Player player1 = new Player(playerName);
        Player player2 = (gameMode.equals("Player vs CPU")) ? new Player("CPU") : new Player("Player 2");
        Game game = new Game(player1, player2);
        games.put(game.getGameId(), game);
        return game.getGameId();
    }

    public String playMove(Socket clientSocket, String move) {
        String gameId = playerSockets.get(clientSocket);
        Game game = games.get(gameId);

        if (game.getPlayer2().getName().equals("CPU")) {
            String cpuMove = getRandomMove();
            return determineWinner(game, move, cpuMove);
        } else {
            if (game.getPlayer1Move() == null) {
                game.setPlayer1Move(move);
                return "Waiting for Player 2 to make a move...";
            } else {
                return determineWinner(game, game.getPlayer1Move(), move);
            }
        }
    }

    private String getRandomMove() {
        String[] moves = {"Rock", "Paper", "Scissors"};
        return moves[random.nextInt(moves.length)];
    }

    private String determineWinner(Game game, String player1Move, String player2Move) {
        String result;
        if (player1Move.equals(player2Move)) {
            result = "Draw";
        } else if ((player1Move.equals("Rock") && player2Move.equals("Scissors")) ||
                (player1Move.equals("Scissors") && player2Move.equals("Paper")) ||
                (player1Move.equals("Paper") && player2Move.equals("Rock"))) {
            result = "Player 1 wins";
        } else {
            result = "Player 2 wins";
        }
        game.setResult(result);
        saveGameResult(game);
        return result;
    }

    private void saveGameResult(Game game) {
        String sql = "INSERT INTO games(id, player1, player2, result) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, game.getGameId());
            pstmt.setString(2, game.getPlayer1().getName());
            pstmt.setString(3, game.getPlayer2().getName());
            pstmt.setString(4, game.getResult());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getStats(Socket clientSocket) {
        String playerName = playerSockets.get(clientSocket);
        StringBuilder stats = new StringBuilder();
        String sql = "SELECT * FROM players WHERE name = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stats.append("Player: ").append(rs.getString("name")).append("\n")
                        .append("Wins: ").append(rs.getInt("wins")).append("\n")
                        .append("Losses: ").append(rs.getInt("losses")).append("\n")
                        .append("Draws: ").append(rs.getInt("draws")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stats.toString();
    }
}
