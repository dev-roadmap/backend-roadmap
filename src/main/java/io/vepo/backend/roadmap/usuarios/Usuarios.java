package io.vepo.backend.roadmap.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuarios {

	private List<Usuario> usuario;

	public Usuarios() {
		this(new ArrayList<>());
	}

	public Usuarios(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
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
		Usuarios other = (Usuarios) obj;
		return Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return String.format("Usuarios [usuario=%s]", usuario);
	}

	public static Usuarios from(List<Usuario> usuarios) {
		return new Usuarios(usuarios);
	}

}