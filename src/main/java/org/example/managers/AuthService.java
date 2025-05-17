package org.example.managers;

import org.example.functional.PasswordUtils;
import org.example.functional.UserDAO;
import org.example.model.User;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class AuthService {
    private final UserDAO userDao;

    public AuthService(UserDAO dao) {
        this.userDao = dao;
    }

    /** Зарегистрировать нового пользователя. */
    public User register(String login, String plainPassword) throws SQLException {
        if (userDao.findByLogin(login) != null) {
            throw new IllegalArgumentException("Пользователь уже существует");
        }
        String hash = PasswordUtils.hashMd2(plainPassword);
        LocalDateTime now = LocalDateTime.now();
        User u = new User(0, login, hash, now);
        long id = userDao.create(u);
        u.setId(id);
        return u;
    }

    /** Проверить логин/пароль, вернуть User или null. */
    public User authenticate(String login, String plainPassword) throws SQLException {
        User u = userDao.findByLogin(login);
        if (u == null) return null;
        String hash = PasswordUtils.hashMd2(plainPassword);
        return hash.equals(u.getPasswordHash()) ? u : null;
    }
}
