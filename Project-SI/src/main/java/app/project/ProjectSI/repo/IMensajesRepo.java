package app.project.ProjectSI.repo;

import app.project.ProjectSI.model.Mensajes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMensajesRepo extends JpaRepository<Mensajes, Long> {
}
