package io.vepo.backend.roadmap.usuarios;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {

    private Set<Usuario> usuarios;

    @PostConstruct
    void setup(){
        usuarios = new HashSet<>();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Usuario> listarUsuarios() {
        return usuarios.stream()
                       .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Usuario encontrarUsuarioPorId(@PathParam("id") Long id) {
        return usuarios.stream()
                       .filter(user -> user.getId().equals(id))
                       .findFirst()
                       .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    @GET
    @Path("/username/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Usuario encontrarUsuarioPorUsername(@PathParam("username") String username) {
        return usuarios.stream()
                       .filter(usuario -> usuario.getUsername().equalsIgnoreCase(username))
                       .findFirst()
                       .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }
}