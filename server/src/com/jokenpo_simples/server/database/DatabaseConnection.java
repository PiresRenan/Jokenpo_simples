package com.jokenpo_simples.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:jokenpo.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Registrar o driver JDBC explicitamente
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void initializeDatabase() {
        String sqlCreateGamesTable = "CREATE TABLE IF NOT EXISTS games (" +
                "id TEXT PRIMARY KEY," +
                "player1 TEXT," +
                "player2 TEXT," +
                "result TEXT" +
                ");";

        String sqlCreatePlayersTable = "CREATE TABLE IF NOT EXISTS players (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT," +
                "wins INTEGER," +
                "losses INTEGER," +
                "draws INTEGER" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreateGamesTable);
            stmt.execute(sqlCreatePlayersTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
