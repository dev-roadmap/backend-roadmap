package io.vepo.backend.roadmap.usuarios;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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

import io.smallrye.mutiny.Uni;
import io.vepo.backend.roadmap.tickets.TicketUsuarioEndpoint;

@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<UsuarioResponse>> listarUsuariosComoJson() {
        return usuarioService.listar()
                             .map(usuarios -> usuarios.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId().toHexString(), usuario.getUsername(), usuario.getEmail());
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Uni<UsuariosResponse> listarUsuariosComoXml() {
        return usuarioService.listar()
                             .map(usuarios -> usuarios.stream().map(this::toResponse).collect(Collectors.toList()))
                             .map(UsuariosResponse::from);
    }

    @GET
    @Path("/{id}")
    @Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    public Uni<UsuarioResponse> encontrarUsuarioPorId(@PathParam("id") String id) {
        return usuarioService.encontrarPorId(id)
                             .onItem().transform(this::toResponse)
                             .ifNoItem().after(Duration.ofMillis(150))
                             .failWith(() -> new NotFoundException("Usuário não encontrado"));

    }

    @GET
    @Path("/username/{username}")
    @Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    public Uni<UsuarioResponse> encontrarUsuarioPorUsername(@PathParam("username") String username) {
        return usuarioService.encontrarPorUsername(username)
                             .onItem().transform(this::toResponse)
                             .ifNoItem().after(Duration.ofMillis(150))
                             .failWith(() -> new NotFoundException("Usuário não encontrado"));
    }

    @PUT
    @Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    public Uni<Response> criarUsuario(CriarUsuarioRequest request) {

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setEmail(request.getEmail());
        usuario.setUsername(request.getUsername());
        return this.usuarioService.salvar(usuario)
                                  .map(this::toResponse)
                                  .map(entity -> Response.status(HttpStatus.SC_CREATED)
                                                         .entity(entity)
                                                         .build());
    }

    @Inject
    TicketUsuarioEndpoint ticketUsuario;

    @Path("{usuarioId}/tickets")
    public TicketUsuarioEndpoint ticketsPorUsuario() {
        return ticketUsuario;
    }
}