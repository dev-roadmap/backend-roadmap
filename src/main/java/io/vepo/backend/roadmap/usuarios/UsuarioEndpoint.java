package io.vepo.backend.roadmap.usuarios;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> listarUsuariosComoJson() {
        return usuarioService.listar();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Usuarios listarUsuariosComoXml() {
        return Usuarios.from(usuarioService.listar());
    }

    @GET
    @Path("/{id}")
    @Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    public Usuario encontrarUsuarioPorId(@PathParam("id") Long id) {
        return usuarioService.encontrarPorId(id)
                             .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    }

    @GET
    @Path("/username/{username}")
    @Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    public Usuario encontrarUsuarioPorUsername(@PathParam("username") String username) {
        return usuarioService.encontrarPorUsername(username)
                             .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    @PUT
    @Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    public Response criarUsuario(CriarUsuarioRequest request) {
        Optional<Usuario> talvezUsuario = this.usuarioService.encontrarPorEmail(request.getEmail());

        if (talvezUsuario.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setEmail(request.getEmail());
            usuario.setUsername(request.getUsername());
            return Response.status(HttpStatus.SC_CREATED).entity(this.usuarioService.salvar(usuario)).build();
        } else {
            return Response.ok().entity(talvezUsuario.get()).build();
        }
    }
}