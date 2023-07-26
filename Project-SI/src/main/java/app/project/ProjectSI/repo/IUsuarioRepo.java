package app.project.ProjectSI.repo;

import app.project.ProjectSI.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepo extends JpaRepository<Usuario, Long> {
    Usuario findByNombre(String nombre);
}
