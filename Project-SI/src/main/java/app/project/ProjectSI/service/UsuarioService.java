package app.project.ProjectSI.service;

import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.repo.IUsuarioRepo;
import app.project.ProjectSI.request.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepo usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public UsuarioDTO get_usuario_service() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername(usuarioActual.getUsername());
        usuarioDTO.setEmail(usuarioActual.getEmail());
        usuarioDTO.setTelefono(usuarioActual.getTelefono());
        usuarioDTO.setPassword(usuarioActual.getPassword());
        usuarioDTO.setFechaNacimiento(usuarioActual.getFechaNacimiento());
        usuarioDTO.setRol(usuarioActual.getRol().name());
        return usuarioDTO;
    }

    public Optional<Usuario> find_usuario_service(String username) {
        return usuarioRepo.findByUsername(username);
    }

    public Usuario create_usuario_service(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public Usuario update_usuario_service(Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioExistente = (Usuario) authentication.getPrincipal();
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioExistente.setTelefono(usuario.getTelefono());
        return usuarioRepo.save(usuarioExistente);
    }

    //CONTACTOS ENPOINTS

    @Transactional
    public UsuarioDTO add_contacto_service(Long contactoID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario contacto = usuarioRepo.findById(contactoID).orElseThrow();

        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        usuarioConContactos.getContactos().add(contacto);
        usuarioRepo.save(usuarioConContactos);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername(contacto.getUsername());
        usuarioDTO.setEmail(contacto.getEmail());
        usuarioDTO.setFechaNacimiento(contacto.getFechaNacimiento());
        return usuarioDTO;
    }

    public Set<Usuario> get_all_contactos_service() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        return usuarioConContactos.getContactos();
    }

    public String delete_contacto_service(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        usuarioConContactos.getContactos().removeIf(contacto -> contacto.getId().equals(id));
        usuarioRepo.save(usuarioConContactos);
        return "Contacto eliminado";
    }

    //SEGUIDOS ENPOINTS

    public UsuarioDTO add_seguido_service(Long contactoID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario seguido = usuarioRepo.findById(contactoID).orElseThrow();

        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        usuarioConContactos.getSeguidos().add(seguido);
        usuarioRepo.save(usuarioConContactos);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername(seguido.getUsername());
        usuarioDTO.setEmail(seguido.getEmail());
        usuarioDTO.setFechaNacimiento(seguido.getFechaNacimiento());
        return usuarioDTO;
    }

    public Set<Usuario> get_all_seguidos_service() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        return usuarioConContactos.getSeguidos();
    }

    public String delete_seguido_service(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        usuarioConContactos.getSeguidos().removeIf(seguido -> seguido.getId().equals(id));
        usuarioRepo.save(usuarioConContactos);
        return "Seguido eliminado";
    }
}
