package io.vepo.backend.roadmap.usuarios;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class CriarUsuarioRequest {
	private String username;
	private String email;

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
		return Objects.hash(email, username);
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
		return Objects.equals(email, other.email) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return String.format("CriarUsuarioRequest [username=%s, email=%s]", username, email);
	}
}
