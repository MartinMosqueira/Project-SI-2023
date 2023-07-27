package app.project.ProjectSI.repo;

import app.project.ProjectSI.model.UsuarioRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRolesRepo extends JpaRepository<UsuarioRoles, Long> {
}
