package com.ino.section03;

public enum UserRole {
    USER('U'),
    ADMIN('A');

    private final char auth;

    UserRole(char auth) {
        this.auth = auth;
    }

    public char getAuth() {
        return auth;
    }
}
