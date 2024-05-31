package com.jokenpo_simples.server.controller;

import com.jokenpo_simples.server.model.Jogador;
import com.jokenpo_simples.server.model.Resultado;
import com.jokenpo_simples.server.model.Jogada;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorBancoDados {
    private static final String URL_CONEXAO = "jdbc:sqlite:jokenpo.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            criarBancoDeDadosSeNaoExistir();
            criarTabelaJogadoresSeNaoExistir();
            criarTabelaHistoricoPartidasSeNaoExistir();
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver SQLite: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void criarBancoDeDadosSeNaoExistir() {
        File dbFile = new File("jokenpo.db");
        if (!dbFile.exists()) {
            try (Connection conn = DriverManager.getConnection(URL_CONEXAO)) {
                System.out.println("Banco de dados SQLite 'jokenpo.db' criado com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao criar o banco de dados: " + e.getMessage());
            }
        }
    }

    private static void criarTabelaJogadoresSeNaoExistir() {
        try (Connection conn = obterConexao()) {
            String sql = "CREATE TABLE IF NOT EXISTS jogadores (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "vitorias INTEGER DEFAULT 0," +
                    "derrotas INTEGER DEFAULT 0," +
                    "empates INTEGER DEFAULT 0" +
                    ")";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela 'jogadores': " + e.getMessage());
        }
    }

    private static void criarTabelaHistoricoPartidasSeNaoExistir() throws SQLException {
        try (Connection conn = obterConexao()) {
            String sql = "CREATE TABLE IF NOT EXISTS historico_partidas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_jogador INTEGER," +
                    "jogada_jogador TEXT NOT NULL," +
                    "jogada_cpu TEXT NOT NULL," +
                    "resultado TEXT NOT NULL," +
                    "FOREIGN KEY(id_jogador) REFERENCES jogadores(id)" +
                    ")";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL_CONEXAO);
    }

    public static Jogador criarJogador(Connection conn, String nome) throws SQLException {
        String sql = "INSERT OR IGNORE INTO jogadores (nome) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Jogador(id, nome, 0, 0, 0);
                } else {
                    sql = "SELECT * FROM jogadores WHERE nome = ?";
                    try (PreparedStatement stmt2 = conn.prepareStatement(sql)) {
                        stmt2.setString(1, nome);
                        try (ResultSet rs2 = stmt2.executeQuery()) {
                            if (rs2.next()) {
                                return new Jogador(rs2.getInt("id"), nome, rs2.getInt("vitorias"),
                                        rs2.getInt("derrotas"), rs2.getInt("empates"));
                            }
                        }
                    }
                }
            }
        }
        throw new SQLException("Falha ao criar ou obter jogador");
    }

    public static void atualizarEstatisticas(Connection conn, int idJogador, Resultado resultado) throws SQLException {
        String coluna = switch (resultado) {
            case VITORIA -> "vitorias";
            case DERROTA -> "derrotas";
            case EMPATE -> "empates";
        };

        String sql = "UPDATE jogadores SET " + coluna + " = " + coluna + " + 1 WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJogador);
            stmt.executeUpdate();
        }
    }

    public static Jogador obterJogador(Connection conn, int idJogador) throws SQLException {
        String sql = "SELECT * FROM jogadores WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJogador);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Jogador jogador = new Jogador();
                    jogador.setId(rs.getInt("id"));
                    jogador.setNome(rs.getString("nome"));
                    jogador.setVitorias(rs.getInt("vitorias"));
                    jogador.setDerrotas(rs.getInt("derrotas"));
                    jogador.setEmpates(rs.getInt("empates"));
                    return jogador;
                }
            }
        }
        throw new SQLException("Jogador não encontrado: " + idJogador);
    }

    public static String obterEstatisticas(Connection conn, int idJogador) throws SQLException {
        String sql = "SELECT resultado FROM historico_partidas WHERE id_jogador = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJogador);
            try (ResultSet rs = stmt.executeQuery()) {
                int vitorias = 0, derrotas = 0, empates = 0;
                while (rs.next()) {
                    String resultado = rs.getString("resultado");
                    switch (Resultado.valueOf(resultado)) {
                        case VITORIA:
                            vitorias++;
                            break;
                        case DERROTA:
                            derrotas++;
                            break;
                        case EMPATE:
                            empates++;
                            break;
                    }
                }
                return String.format("Vitorias: %d, Derrotas: %d, Empates: %d", vitorias, derrotas, empates);
            }
        }
    }

    public static void adicionarPartidaAoHistorico(Connection conn, int idJogador, Jogada jogadaJogador, Jogada jogadaCpu, Resultado resultado) throws SQLException {
        String sql = "INSERT INTO historico_partidas (id_jogador, jogada_jogador, jogada_cpu, resultado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idJogador);
            stmt.setString(2, jogadaJogador.name());
            stmt.setString(3, jogadaCpu.name());
            stmt.setString(4, resultado.name());
            stmt.executeUpdate();
        }
    }
}