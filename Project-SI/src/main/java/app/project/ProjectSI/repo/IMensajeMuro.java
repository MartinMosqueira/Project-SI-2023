package app.project.ProjectSI.repo;

import app.project.ProjectSI.model.MensajeMuro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMensajeMuro extends JpaRepository<MensajeMuro, Long> {
}
