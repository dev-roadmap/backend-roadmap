package io.vepo.backend.roadmap.usuarios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class UsuarioService {

    @Inject
    Usuarios usuarios;

    public Uni<List<Usuario>> listar() {
        return usuarios.listAll();
    }

    public Uni<Usuario> encontrarPorId(String id) {
        return usuarios.findById(new ObjectId(id));
    }

    public Uni<Usuario> encontrarPorUsername(String username) {
        return usuarios.find("username", username).firstResult();
    }

    public Uni<Usuario> encontrarPorEmail(String email) {
        return usuarios.find("email", email).firstResult();
    }

    public Uni<Usuario> salvar(UsuarioResponse usuario) {
        return usuarios.persist(new Usuario(null, usuario.getUsername(), usuario.getEmail()));
    }

}
