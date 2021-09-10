package io.vepo.backend.roadmap.tickets;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;

@ApplicationScoped
public class Tickets implements ReactivePanacheMongoRepository<Ticket> {

}
