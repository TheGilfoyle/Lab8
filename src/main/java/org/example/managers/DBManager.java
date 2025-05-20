package org.example.managers;


import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.model.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class DBManager {
    private static Connection conn;
    private static ScriptRunner runner;
    private String currentUser;

    public void connect() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/studs",
                    "s465199",
                    System.getenv("SQL")
            );
            runner = new ScriptRunner(conn);
            setScriptRunnerConfig();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try (Reader fr = Files.newBufferedReader(
                Paths.get("src/main/resources/sql/create.sql"),
                StandardCharsets.UTF_8
        )) {
            runner.runScript(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashSet<MusicBand> getMusicBands() {
        HashSet<MusicBand> bands = new HashSet<>();
        String sql = "SELECT id, name, coord_x, coord_y, creation_date, number_of_participants, genre, studio_name, created_by FROM music_bands";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MusicBand band = new MusicBand();
                band.setId(rs.getInt("id"));
                band.setName(rs.getString("name"));

                Coordinates coords = new Coordinates();
                coords.setX(rs.getInt("coord_x"));
                coords.setY(rs.getLong("coord_y"));
                band.setCoordinates(coords);

                band.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());

                band.setNumberOfParticipants(rs.getLong("number_of_participants"));

                String genreStr = rs.getString("genre");
                if (genreStr != null) band.setGenre(MusicGenre.valueOf(genreStr));

                String studioName = rs.getString("studio_name");
                if (studioName != null) {
                    Studio studio = new Studio();
                    studio.setName(studioName);
                    band.setStudio(studio);
                }

                bands.add(band);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bands;
    }

    public Map<String, User> getUsers() {
        Map<String, User> users = new HashMap<>();
        String sql = "SELECT id, login, password_hash, created_at FROM users";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String login = rs.getString("login");
                String hash = rs.getString("password_hash");
                LocalDateTime created = rs.getTimestamp("created_at")
                        .toLocalDateTime();
                users.put(
                        login,
                        new User(id, login, hash, created)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    public void registerUser(String username, String password) {
        try {
            conn.setAutoCommit(false);
            if (getUsers().containsKey(username)) {
                System.out.println("Пользователь '" + username + "' уже существует.");
            } else {
                String sql = "INSERT INTO users (login, password_hash) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, username);
                    ps.setString(2, hashPassword(password));
                    if (ps.executeUpdate() > 0) {
                        conn.commit();
                        System.out.println("Пользователь '" + username + "' успешно зарегистрирован.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean login(String username, String password) {
        boolean authenticated = false;
        try {
            conn.setAutoCommit(false);
            String sql = "SELECT password_hash FROM users WHERE login = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String stored = rs.getString("password_hash");
                        authenticated = stored.equals(hashPassword(password));
                    }
                }
            }
            if (authenticated) this.currentUser = username;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (!authenticated) System.out.println("Неверный пароль или логин.");
        return authenticated;
    }


    public boolean addMusicBand(MusicBand band, String username) {
        boolean success = false;
        if (!getUsers().containsKey(username)) {
            System.err.println("Нельзя добавить группу: пользователь '" + username + "' не найден.");
            return false;
        }
        try {
            conn.setAutoCommit(false);
            int newId = findMinFreeId();
            String sql = "INSERT INTO music_bands "
                    + "(id,name,coord_x,coord_y,number_of_participants,genre,studio_name,created_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?::music_genre, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, newId);
                ps.setString(2, band.getName());
                ps.setInt(3, band.getCoordinates().getX());
                ps.setLong(4, band.getCoordinates().getY());
                ps.setLong(5, band.getNumberOfParticipants());
                ps.setString(6, band.getGenre() != null ? band.getGenre().name() : null);
                ps.setString(7, band.getStudio() != null
                        ? band.getStudio().getName()
                        : null);
                ps.setString(8, username);
                if (ps.executeUpdate() == 0) throw new SQLException("Не получилось создать музыкальную группу...");
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) band.setId(keys.getInt(1));
                    else throw new SQLException("Не получилось создать музыкальную группу...");
                }
            }
            conn.commit();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean addMusicBand(MusicBand band) {
        return addMusicBand(band, currentUser);
    }

    private int findMinFreeId() throws SQLException {
        String sql = "SELECT id FROM music_bands ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int expected = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                if (id == expected) {
                    expected++;
                } else if (id > expected) {
                    break;
                }
            }
            return expected;
        }
    }


    public boolean updateMusicBand(int id, MusicBand band, String username) {
        boolean success = false;
        try {
            conn.setAutoCommit(false);
            String owner = null;
            String chk = "SELECT created_by FROM music_bands WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(chk)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) owner = rs.getString("created_by");
                }
            }
            if (!username.equals(owner)) {
                System.err.println("Отклонено по правам доступа или неверная группа");
                return false;
            }
            String sql = "UPDATE music_bands SET name = ?, coord_x = ?, coord_y = ?, " +
                    "number_of_participants = ?, genre = ?, studio_name = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, band.getName());
                ps.setInt(2, band.getCoordinates().getX());
                ps.setLong(3, band.getCoordinates().getY());
                ps.setLong(4, band.getNumberOfParticipants());
                if (band.getGenre() != null) {
                    ps.setObject(5, band.getGenre().name(), java.sql.Types.OTHER);
                } else {
                    ps.setNull(5, java.sql.Types.OTHER);
                }
                ps.setString(6, band.getStudio().getName());
                ps.setInt(7, id);
                if (ps.executeUpdate() == 0)
                    throw new SQLException("Обновление данных музыкальной группы провалено...");
            }
            conn.commit();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public int removeLower(String username, long threshold) {
        String sql =
                "DELETE FROM music_bands " +
                        "WHERE created_by = ? AND number_of_participants < ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setLong(2, threshold);
            int deleted = ps.executeUpdate();
            return deleted;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean removeByID(String username, int id) {
        boolean success = false;
        try {
            conn.setAutoCommit(false);
            String owner = null;
            String chk = "SELECT created_by FROM music_bands WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(chk)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) owner = rs.getString("created_by");
                }
            }
            if (!username.equals(owner)) {
                System.err.println("Невозможно удалить: элемент не найден либо вы пытаетесь удалить чужой труд, это не хорошо(.");
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM music_bands WHERE id = ?")) {
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    conn.commit();
                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean clear(String username) {
        boolean success = false;
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM music_bands WHERE created_by = ?")) {
                ps.setString(1, username);
                ps.executeUpdate();
            }
            conn.commit();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Хеширование пароля MD2 → hex-string
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD2 not available", e);
        }
    }

    private void setScriptRunnerConfig() {
        runner.setStopOnError(true);
        runner.setAutoCommit(false);
        runner.setLogWriter(null);
        runner.setSendFullScript(false);
    }
}
