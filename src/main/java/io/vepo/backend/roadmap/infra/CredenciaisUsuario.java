package io.vepo.backend.roadmap.infra;

import io.quarkus.security.identity.request.BaseAuthenticationRequest;

public class CredenciaisUsuario extends BaseAuthenticationRequest {
    private String username;
    private String password;

    public CredenciaisUsuario() {
    }

    public CredenciaisUsuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
