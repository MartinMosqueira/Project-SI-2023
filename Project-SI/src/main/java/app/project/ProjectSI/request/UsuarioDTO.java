package app.project.ProjectSI.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private LocalDate fechaNacimiento;
    @NotBlank
    private String password;
    private Long telefono;
    private String rol;
}
