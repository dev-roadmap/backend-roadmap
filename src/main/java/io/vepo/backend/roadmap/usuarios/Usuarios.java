package io.vepo.backend.roadmap.usuarios;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;

@ApplicationScoped
public class Usuarios implements ReactivePanacheMongoRepository<Usuario> {

}
