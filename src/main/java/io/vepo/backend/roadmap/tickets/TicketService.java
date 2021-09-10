package io.vepo.backend.roadmap.tickets;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class TicketService {

    @Inject
    Tickets tickets;

    public Multi<Ticket> listar() {
        return tickets.findAll().stream();
    }

    public Uni<Ticket> criar(String titulo, String descricao, String reporterId, String assigneeId) {
        return tickets.persist(new Ticket(null, titulo, descricao,
                                          Optional.ofNullable(reporterId).map(ObjectId::new).orElse(null),
                                          Optional.ofNullable(assigneeId).map(ObjectId::new).orElse(null)));
    }

    public Multi<Ticket> listarDoUsuario(String usuarioId) {
        return tickets.find("reporterId = ?1 or assigneeId = ?1", usuarioId)
                      .stream();
    }

}
