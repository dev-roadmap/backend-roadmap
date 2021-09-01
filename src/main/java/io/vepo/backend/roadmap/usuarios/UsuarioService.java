package io.vepo.backend.roadmap.usuarios;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioService {

    private Set<Usuario> usuarios;

    @PostConstruct
    void setup() {
        usuarios = new HashSet<>();
    }

    public List<Usuario> listar() {
        return usuarios.stream().collect(Collectors.toList());
    }

    public Optional<Usuario> encontrarPorId(Long id) {
        return usuarios.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public Optional<Usuario> encontrarPorUsername(String username) {
        return usuarios.stream().filter(usuario -> usuario.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    public Optional<Usuario> encontrarPorEmail(String email) {
        return this.usuarios.stream()
                            .filter(usuario -> email.compareToIgnoreCase(usuario.getEmail()) == 0)
                            .findFirst();
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(this.usuarios.stream().mapToLong(Usuario::getId).max().orElse(0L) + 1L);
            this.usuarios.add(usuario);
        } else {
            this.usuarios.removeIf(usr -> usr.getId().equals(usuario.getId()));
            this.usuarios.add(usuario);
        }
        return usuario;
    }

    void cleanup() {
        this.usuarios.clear();
    }

}
