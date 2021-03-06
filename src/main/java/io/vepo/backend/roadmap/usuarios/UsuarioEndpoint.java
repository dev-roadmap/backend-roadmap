package io.vepo.backend.roadmap.usuarios;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.vepo.backend.roadmap.infra.JwtUtils;
import io.vepo.backend.roadmap.infra.Roles;
import org.apache.http.HttpStatus;

import io.smallrye.mutiny.Uni;
import io.vepo.backend.roadmap.tickets.TicketUsuarioEndpoint;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@DenyAll
@Path("/usuario")
@ApplicationScoped
public class UsuarioEndpoint {

    @Inject
    UsuarioService usuarioService;

    @GET
    @RolesAllowed(Roles.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<UsuarioResponse>> listarUsuariosComoJson() {
        return usuarioService.listar()
                             .map(usuarios -> usuarios.stream().map(this::toResponse).collect(Collectors.toList()));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId().toHexString(), usuario.getUsername(),
                                   usuario.getEmail(), usuario.getRoles());
    }

    @GET
    @RolesAllowed(Roles.ADMIN)
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
            MediaType.APPLICATION_XML})
    public Uni<UsuarioResponse> encontrarUsuarioPorId(@PathParam("id") String id) {
        return usuarioService.encontrarPorId(id)
                             .onItem().transform(this::toResponse)
                             .ifNoItem().after(Duration.ofMillis(150))
                             .failWith(() -> new NotFoundException("Usu??rio n??o encontrado"));

    }

    @GET
    @Path("/username/{username}")
    @Produces({
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML})
    public Uni<UsuarioResponse> encontrarUsuarioPorUsername(@PathParam("username") String username) {
        return usuarioService.encontrarPorUsername(username)
                             .onItem().transform(this::toResponse)
                             .ifNoItem().after(Duration.ofMillis(150))
                             .failWith(() -> new NotFoundException("Usu??rio n??o encontrado"));
    }

    @PUT
    @PermitAll
    @Produces({
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML})
    @APIResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = UsuariosResponse.class))})
    public Uni<Response> criarUsuario(CriarUsuarioRequest request) {
        return this.usuarioService.salvar(new Usuario(null, request.getUsername(), request.getEmail(), request.getRoles(), request.getPassword()))
                                  .map(this::toResponse)
                                  .map(entity -> Response.status(HttpStatus.SC_CREATED)
                                                         .entity(entity)
                                                         .build());
    }

    @Inject
    JwtUtils jwtUtils;

    @POST
    @PermitAll
    @Path("login")
    @Consumes({
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML})
    @Produces({
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML})
    public Uni<Response> login(Credencial credencial) {
        return this.usuarioService.encontrarPorUsernamePassword(credencial.getUsername(), credencial.getPassword())
                                  .map(usuario -> Response.ok()
                                                          .entity(Autenticacao.builder()
                                                                              .id(usuario.getId().toHexString())
                                                                              .roles(usuario.getRoles())
                                                                              .username(usuario.getUsername())
                                                                              .token(jwtUtils.generate(usuario))
                                                                              .build())
                                                          .build());
    }

    @Inject
    TicketUsuarioEndpoint ticketUsuario;

    @Path("{usuarioId}/ticket")
    public TicketUsuarioEndpoint ticketsPorUsuario() {
        return ticketUsuario;
    }
}