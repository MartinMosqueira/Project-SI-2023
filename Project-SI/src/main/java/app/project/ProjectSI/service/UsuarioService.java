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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        usuarioDTO.setId(usuarioActual.getId());
        usuarioDTO.setUsername(usuarioActual.getUsername());
        usuarioDTO.setEmail(usuarioActual.getEmail());
        usuarioDTO.setTelefono(usuarioActual.getTelefono());
        usuarioDTO.setPassword(usuarioActual.getPassword());
        usuarioDTO.setFechaNacimiento(usuarioActual.getFechaNacimiento());
        usuarioDTO.setRol(usuarioActual.getRol().name());
        return usuarioDTO;
    }

    public UsuarioDTO find_usuario_service(String username) {
        Usuario usuario = usuarioRepo.findByUsername(username).orElseThrow();;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioDTO.setTelefono(usuario.getTelefono());
        return usuarioDTO;
    }

    public Usuario create_usuario_service(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public UsuarioDTO update_usuario_service(Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioExistente = (Usuario) authentication.getPrincipal();
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioRepo.save(usuarioExistente);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername(usuarioExistente.getUsername());
        usuarioDTO.setEmail(usuarioExistente.getEmail());
        usuarioDTO.setFechaNacimiento(usuarioExistente.getFechaNacimiento());
        usuarioDTO.setTelefono(usuarioExistente.getTelefono());
        usuarioDTO.setPassword(usuarioExistente.getPassword());
        return usuarioDTO;
    }

    //CONTACTOS ENPOINTS

    public String add_contacto_service(Long contactoID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Usuario contactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        contactos.getContactosIds().add(contactoID);
        usuarioRepo.save(contactos);
        return "Contacto añadido";
    }

    public Set<UsuarioDTO> get_all_contactos_service() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario contactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();

        return contactos.getContactosIds().stream()
                .filter(contactoId -> !contactoId.equals(usuario.getId())) // Filtra los IDs diferentes al del usuario autenticado
                .map(contactoId -> usuarioRepo.findById(contactoId)
                        .orElseThrow(() -> new RuntimeException("Contacto no encontrado")))
                .map(contacto -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setId(contacto.getId());
                    usuarioDTO.setUsername(contacto.getUsername());
                    usuarioDTO.setEmail(contacto.getEmail());
                    return usuarioDTO;
                })
                .collect(Collectors.toSet());
    }

    public String delete_contacto_service(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        usuarioConContactos.getContactosIds().removeIf(contacto -> contacto.equals(id));
        usuarioRepo.save(usuarioConContactos);
        return "Contacto eliminado";
    }

    //SEGUIDOS ENPOINTS

    public String add_seguido_service(Long contactoID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Usuario seguidos = usuarioRepo.findWithSeguidosById(usuario.getId()).orElseThrow();
        seguidos.getSeguidosIds().add(contactoID);
        usuarioRepo.save(seguidos);
        return "Seguido añadido";
    }

    public Set<UsuarioDTO> get_all_seguidos_service() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario seguidos = usuarioRepo.findWithSeguidosById(usuario.getId()).orElseThrow();

        return seguidos.getSeguidosIds().stream()
                .filter(seguidoId -> !seguidoId.equals(usuario.getId())) // Filtra los IDs diferentes al del usuario autenticado
                .map(seguidoId -> usuarioRepo.findById(seguidoId)
                        .orElseThrow(() -> new RuntimeException("Seguido no encontrado")))
                .map(seguido -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    usuarioDTO.setId(seguido.getId());
                    usuarioDTO.setUsername(seguido.getUsername());
                    usuarioDTO.setEmail(seguido.getEmail());
                    return usuarioDTO;
                })
                .collect(Collectors.toSet());
    }

    public String delete_seguido_service(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuario.getId()).orElseThrow();
        usuarioConContactos.getSeguidosIds().removeIf(seguido -> seguido.equals(id));
        usuarioRepo.save(usuarioConContactos);
        return "Seguido eliminado";
    }
}
