package io.vepo.backend.roadmap.usuarios;

import java.util.Objects;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "usuario")
public class Usuario {
    private ObjectId id;
    private String username;
    private String email;

    public Usuario() {
    }

    public Usuario(ObjectId id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id, username);
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
        Usuario other = (Usuario) obj;
        return Objects.equals(email, other.email) && Objects.equals(id, other.id)
                && Objects.equals(username, other.username);
    }

    @Override
    public String toString() {
        return String.format("Usuario [id=%s, username=%s, email=%s]", id, username, email);
    }

}
