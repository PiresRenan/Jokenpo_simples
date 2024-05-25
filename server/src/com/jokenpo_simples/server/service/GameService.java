package com.jokenpo_simples.server.service;

import com.jokenpo_simples.server.database.DatabaseConnection;
import com.jokenpo_simples.server.model.Game;
import com.jokenpo_simples.server.model.Player;

import java.io.IOException;
import java.io.PrintWriter;
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

    public String createGame(String playerName, String gameMode, Socket socket) {
        Player player1 = new Player(playerName, socket);
        Player player2 = (gameMode.equals("Player vs CPU")) ? new Player("CPU", null) : new Player("Player 2", null);
        Game game = new Game(player1, player2);
        games.put(game.getGameId(), game);
        playerSockets.put(socket, game.getGameId());
        return game.getGameId();
    }

    public String joinOrCreateGame(String playerName, Socket socket) {
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            Game game = entry.getValue();
            if (game.getPlayer2().getName().equals("Player 2") && game.getPlayer2().getSocket() == null) {
                game.getPlayer2().setName(playerName);
                game.getPlayer2().setSocket(socket);
                playerSockets.put(socket, game.getGameId());
                return game.getGameId();
            }
        }
        // If no game is available, create a new one
        return createGame(playerName, "Player vs Player", socket);
    }

    public String playMove(Socket clientSocket, String move) {
        String gameId = playerSockets.get(clientSocket);
        Game game = games.get(gameId);

        if (game.getPlayer2().getName().equals("CPU")) {
            String cpuMove = getRandomMove();
            String result = determineWinner(game, move, cpuMove);
            notifyPlayers(game, result);
            return result;
        } else {
            if (clientSocket.equals(game.getPlayer1().getSocket())) {
                game.setPlayer1Move(move);
                return "Waiting for Player 2 to make a move...";
            } else if (clientSocket.equals(game.getPlayer2().getSocket())) {
                game.setPlayer2Move(move);
                String result = determineWinner(game, game.getPlayer1Move(), move);
                notifyPlayers(game, result);
                return result;
            }
            return "Invalid player move.";
        }
    }

    private void notifyPlayers(Game game, String result) {
        try {
            PrintWriter out1 = new PrintWriter(game.getPlayer1().getSocket().getOutputStream(), true);
            out1.println(result);
            if (game.getPlayer2().getSocket() != null) {
                PrintWriter out2 = new PrintWriter(game.getPlayer2().getSocket().getOutputStream(), true);
                out2.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public String getStats(String playerName) {
        StringBuilder stats = new StringBuilder();
        String sql = "SELECT * FROM games WHERE player1 = ? OR player2 = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setString(2, playerName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stats.append("Game ID: ").append(rs.getString("id")).append("\n")
                        .append("Player 1: ").append(rs.getString("player1")).append("\n")
                        .append("Player 2: ").append(rs.getString("player2")).append("\n")
                        .append("Result: ").append(rs.getString("result")).append("\n\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stats.toString();
    }
}
