package io.vepo.backend.roadmap.usuarios;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {

	private Set<Usuario> usuarios;

	@PostConstruct
	void setup() {
		usuarios = new HashSet<>();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> listarUsuariosComoJson() {
		return usuarios.stream().collect(Collectors.toList());
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Usuarios listarUsuariosComoXml() {
		return Usuarios.from(usuarios.stream().collect(Collectors.toList()));
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Usuario encontrarUsuarioPorId(@PathParam("id") Long id) {
		return usuarios.stream().filter(user -> user.getId().equals(id)).findFirst()
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
	}

	@GET
	@Path("/username/{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Usuario encontrarUsuarioPorUsername(@PathParam("username") String username) {
		return usuarios.stream().filter(usuario -> usuario.getUsername().equalsIgnoreCase(username)).findFirst()
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Usuario criarUsuario(CriarUsuarioRequest request) {
		Optional<Usuario> talvezUsuario = this.usuarios.stream()
				.filter(usuario -> request.getEmail().compareToIgnoreCase(usuario.getEmail()) == 0).findFirst();
		if (talvezUsuario.isEmpty()) {
			Usuario usuario = new Usuario();
			usuario.setId(this.usuarios.stream().mapToLong(Usuario::getId).max().orElse(0L) + 1L);
			usuario.setEmail(request.getEmail());
			usuario.setUsername(request.getUsername());
			this.usuarios.add(usuario);
			return usuario;
		} else {
			return talvezUsuario.get();
		}
	}
}