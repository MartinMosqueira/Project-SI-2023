package app.project.ProjectSI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mensajes")
public class Mensajes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 200)
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha")
    private Instant fecha;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
}
