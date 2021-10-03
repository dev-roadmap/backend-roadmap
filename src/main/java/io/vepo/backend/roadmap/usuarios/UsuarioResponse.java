package io.vepo.backend.roadmap.usuarios;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioResponse {
    private String id;
    private String username;
    private String email;

    public UsuarioResponse() {
    }

    public UsuarioResponse(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
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
        UsuarioResponse other = (UsuarioResponse) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username)
                && Objects.equals(this.email, other.email);
    }

    @Override
    public String toString() {
        return String.format("[Usuario id=%s username=%s email=%s]", this.id, this.username, this.email);
    }
}