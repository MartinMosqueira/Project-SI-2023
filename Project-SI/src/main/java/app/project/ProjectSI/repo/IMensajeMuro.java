package app.project.ProjectSI.repo;

import app.project.ProjectSI.model.MensajeMuro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

public interface IMensajeMuro extends JpaRepository<MensajeMuro, Long> {
    Set<MensajeMuro> findAllByUsuarioId(Long id);
    Set<MensajeMuro> findAllByUsuarioIdIn(Set<Long> ids);
}
