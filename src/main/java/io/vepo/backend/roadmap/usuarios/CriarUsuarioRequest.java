package io.vepo.backend.roadmap.usuarios;

import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class CriarUsuarioRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;

    public CriarUsuarioRequest() {
    }

    public CriarUsuarioRequest(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, password, roles);
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
        CriarUsuarioRequest other = (CriarUsuarioRequest) obj;
        return Objects.equals(email, other.email) && Objects.equals(username, other.username) &&
                Objects.equals(password, other.password) && Objects.equals(roles, other.roles);
    }

    @Override
    public String toString() {
        return String.format("CriarUsuarioRequest [username=%s, email=%s, password=%s, roles=%s]",
                username, email, password, roles);
    }
}
