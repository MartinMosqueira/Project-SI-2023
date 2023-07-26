package app.project.ProjectSI.service;

import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.repo.IUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepo repo;

    private Usuario findByNombre(String nombre) {
        return repo.findByNombre(nombre);
    }
}
