package io.vepo.backend.roadmap.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuariosResponse {

	private List<UsuarioResponse> usuario;

	public UsuariosResponse() {
		this(new ArrayList<>());
	}

	public UsuariosResponse(List<UsuarioResponse> usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioResponse> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<UsuarioResponse> usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario);
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
		UsuariosResponse other = (UsuariosResponse) obj;
		return Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return String.format("Usuarios [usuario=%s]", usuario);
	}

	public static UsuariosResponse from(List<UsuarioResponse> usuarios) {
		return new UsuariosResponse(usuarios);
	}

}