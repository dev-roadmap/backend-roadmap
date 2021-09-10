package io.vepo.backend.roadmap.tickets;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class TicketUsuarioEndpoint {

    @Inject
    TicketService ticketService;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Uni<TicketsResponse> listarTicketsXml(@PathParam("usuarioId") String usuarioId) {
        return ticketService.listarDoUsuario(usuarioId)
                            .onItem()
                            .transform(this::toResponse)
                            .collect()
                            .asList()
                            .map(tickets -> new TicketsResponse(tickets));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<TicketResponse> listarTicketsJson(@PathParam("usuarioId") String usuarioId) {
        return ticketService.listarDoUsuario(usuarioId)
                            .onItem()
                            .transform(this::toResponse);
    }

    private TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(ticket.getId().toHexString(), ticket.getTitulo(), ticket.getDescricao(),
                                  Optional.ofNullable(ticket.getReporterId()).map(ObjectId::toHexString).orElse(null),
                                  Optional.ofNullable(ticket.getAssigneeId()).map(ObjectId::toHexString).orElse(null));
    }
}
