package app.project.ProjectSI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;
    @NotBlank
    @Size(max = 40)
    @Column(name = "apellido")
    private String apellido;
    @Email
    @NotBlank
    @Size(max = 60)
    @Column(name = "email")
    private String email;
    @NotBlank
    @Column(name = "fecha nacimiento")
    private Date fechaNacimiento;
    @NotBlank
    @Column(name = "password")
    private String password;
    @Size(min = 7, max = 13)
    @Column(name = "telefono")
    private Long telefono;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = UsuarioRoles.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<UsuarioRoles> rol;
}
