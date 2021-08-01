package io.vepo.backend.roadmap.user;

import java.util.Objects;

public class User {
    private Long id;
    private String username;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(this.id, other.id) && 
                Objects.equals(this.username, other.username) && 
                Objects.equals(this.email, other.email);
    }

    @Override
    public String toString() {
        return String.format("[User id=%s username=%s email=%s", this.id, this.username, this.email);
    }
}