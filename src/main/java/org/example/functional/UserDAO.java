package org.example.functional;

import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public User findByLogin(String login) throws SQLException {
        String sql = "SELECT id, login, password_hash, created_at FROM users WHERE login = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String dbLogin = rs.getString("login");
                    String hash    = rs.getString("password_hash");
                    LocalDateTime createdAt =
                            rs.getTimestamp("created_at").toLocalDateTime();

                    return new User(id, dbLogin, hash, createdAt);
                }
            }
        }
        return null;
    }

    public long create(User u) throws SQLException {
        String sql = "INSERT INTO users(login, password_hash) VALUES (?,?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getPasswordHash());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("Не удалось получить id при вставке пользователя");
                }
            }
        }
    }

}
