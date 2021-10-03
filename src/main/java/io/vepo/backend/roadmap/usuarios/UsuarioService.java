package io.vepo.backend.roadmap.usuarios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import io.smallrye.mutiny.Uni;
import io.vepo.backend.roadmap.infra.PasswordEncrypter;

@ApplicationScoped
public class UsuarioService {

    @Inject
    Usuarios usuarios;

    @Inject
    PasswordEncrypter passwordEncrypter;

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

    public Uni<Usuario> encontrarPorUsernamePassword(String username, String password) {
        return usuarios.find("username = ?1 and hashedPassword = ?2", username, passwordEncrypter.encrypt(password))
                .firstResult();
    }

    public Uni<Usuario> salvar(Usuario usuario) {
        usuario.setHashedPassword(passwordEncrypter.encrypt(usuario.getHashedPassword()));
        return usuarios.persist(usuario);
    }

}
