package app.project.ProjectSI.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeMuro extends Mensajes{
    @Column(name = "tags")
    private String tags;
}
