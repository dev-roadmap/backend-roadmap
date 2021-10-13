package io.vepo.backend.roadmap.usuarios;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Autenticacao {
    public static class AutenticacaoBuilder {
        private String id;
        private String username;
        private Set<String> roles;
        private String token;

        private AutenticacaoBuilder() {
            roles = new HashSet<>();
        }

        public AutenticacaoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public AutenticacaoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AutenticacaoBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AutenticacaoBuilder role(String role) {
            this.roles.add(role);
            return this;
        }

        public AutenticacaoBuilder roles(Collection<String> roles) {
            if (Objects.nonNull(roles)) {
                this.roles.addAll(roles);
            }
            return this;
        }

        public Autenticacao build() {
            return new Autenticacao(this);
        }

    }

    private String id;
    private String username;
    private Set<String> roles;
    private String token;

    public Autenticacao() {
    }

    private Autenticacao(AutenticacaoBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.roles = builder.roles;
        this.token = builder.token;

    }

    public static AutenticacaoBuilder builder() {
        return new AutenticacaoBuilder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autenticacao that = (Autenticacao) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) &&
                Objects.equals(roles, that.roles) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, roles, token);
    }

    @Override
    public String toString() {
        return String.format("Autenticacao [id='%s', username='%s', roles=%s, token='%s']",
                             id, username, roles, token);
    }
}
