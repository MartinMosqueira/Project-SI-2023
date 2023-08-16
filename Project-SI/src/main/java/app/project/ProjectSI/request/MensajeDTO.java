package app.project.ProjectSI.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {
    @NotBlank
    private String username;
    @NotNull
    private Instant fecha;
    @NotBlank
    private String mensaje;
    private String tags;
}
