package io.vepo.backend.roadmap.tickets;

import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@RolesAllowed("USER")
@Path("/ticket")
@ApplicationScoped
public class TicketEndpoint {

    @Inject
    TicketService ticketService;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Uni<TicketsResponse> listarTicketsXml() {
        return ticketService.listar()
                            .onItem()
                            .transform(this::toResponse)
                            .collect()
                            .asList()
                            .map(tickets -> new TicketsResponse(tickets));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<TicketResponse> listarTicketsJson() {
        return ticketService.listar()
                            .onItem()
                            .transform(this::toResponse);
    }

    @PUT
    @Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    @Consumes({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML })
    public Uni<TicketResponse> novoTicket(CriarTicketRequest requisicao) {
        return ticketService.criar(requisicao.getTitulo(), requisicao.getDescricao(), requisicao.getReporterId(),
                                   requisicao.getAssigneeId())
                            .map(this::toResponse);
    }

    private TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(ticket.getId().toHexString(), ticket.getTitulo(), ticket.getDescricao(),
                                  Optional.ofNullable(ticket.getReporterId()).map(ObjectId::toHexString).orElse(null),
                                  Optional.ofNullable(ticket.getAssigneeId()).map(ObjectId::toHexString).orElse(null));
    }
}
