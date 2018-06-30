package br.com.welson.clinic.persistence.model;

import java.time.LocalDateTime;

public class AccessToken {

    private String token;
    private LocalDateTime expiration;

    public AccessToken() {
    }

    public AccessToken(String token, LocalDateTime expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiration() {
        return expiration.toString();
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
