package app.project.ProjectSI.service;

import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.repo.IUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepo repo;

    public Optional<Usuario> find_username_service(String username) {
        return repo.findByUsername(username);
    }

    public Usuario create_usuario_service(Usuario usuario) {
        return repo.save(usuario);
    }

    public void delete_usuario_service(Long id) {
        repo.deleteById(id);
    }
}
