package org.example.model;

import java.time.LocalDateTime;

public class User {
    private long id;
    private String login;
    private String passwordHash;     // 32-символьный MD2-хеш в hex
    private LocalDateTime createdAt; // дата создания из БД

    public User(long id, String login, String passwordHash, LocalDateTime createdAt) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    // геттеры
    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // сеттеры при необходимости
    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
