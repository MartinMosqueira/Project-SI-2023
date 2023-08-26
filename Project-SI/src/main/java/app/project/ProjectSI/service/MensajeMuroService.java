package app.project.ProjectSI.service;

import app.project.ProjectSI.model.MensajeMuro;
import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.repo.IMensajeMuro;
import app.project.ProjectSI.repo.IUsuarioRepo;
import app.project.ProjectSI.request.MensajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MensajeMuroService {
    private final IMensajeMuro mensajeMuroRepo;
    private final IUsuarioRepo usuarioRepo;

    @Autowired
    public MensajeMuroService(IMensajeMuro mensajeMuroRepo, IUsuarioRepo usuarioRepo) {
        this.mensajeMuroRepo = mensajeMuroRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public MensajeDTO create_mensajeMuro_service(MensajeMuro mensajeMuro){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuarioActual.getId()).orElseThrow();
        mensajeMuro.setUsuario(usuarioConContactos);
        mensajeMuro.setFecha(Instant.now());
        mensajeMuroRepo.save(mensajeMuro);

        MensajeDTO mensajeDTO = new MensajeDTO();
        mensajeDTO.setUsername(mensajeMuro.getUsuario().getUsername());
        mensajeDTO.setFecha(mensajeMuro.getFecha());
        mensajeDTO.setMensaje(mensajeMuro.getMensaje());
        mensajeDTO.setTags(mensajeMuro.getTags());
        return mensajeDTO;
    }

    public MensajeDTO update_mensajeMuro_service(Long id, MensajeMuro mensajeMuro){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        Usuario usuarioConContactos = usuarioRepo.findWithContactosById(usuarioActual.getId()).orElseThrow();
        MensajeMuro mensajeMuroExistente = mensajeMuroRepo.findById(id).orElseThrow( ()-> new NoSuchElementException("Mensaje no encontrado"));
        mensajeMuroExistente.setMensaje(mensajeMuro.getMensaje());
        mensajeMuroExistente.setFecha(Instant.now());
        mensajeMuroExistente.setUsuario(usuarioConContactos);
        mensajeMuroExistente.setTags(mensajeMuro.getTags());
        mensajeMuroRepo.save(mensajeMuroExistente);

        MensajeDTO mensajeDTO = new MensajeDTO();
        mensajeDTO.setUsername(mensajeMuroExistente.getUsuario().getUsername());
        mensajeDTO.setFecha(mensajeMuroExistente.getFecha());
        mensajeDTO.setMensaje(mensajeMuroExistente.getMensaje());
        mensajeDTO.setTags(mensajeMuroExistente.getTags());
        return mensajeDTO;
    }

    public String delete_mensajeMuro_service(Long id){
        mensajeMuroRepo.deleteById(id);
        return "Mensaje eliminado";
    }

    public MensajeDTO get_MensajeMuro_service(Long id){
        MensajeDTO mensajeDTO = new MensajeDTO();
        MensajeMuro mensajeMuro = mensajeMuroRepo.findById(id).orElseThrow( ()-> new NoSuchElementException("Mensaje no encontrado"));
        mensajeDTO.setId(mensajeMuro.getId());
        mensajeDTO.setUsername(mensajeMuro.getUsuario().getUsername());
        mensajeDTO.setMensaje(mensajeMuro.getMensaje());
        mensajeDTO.setFecha(mensajeMuro.getFecha());
        mensajeDTO.setTags(mensajeMuro.getTags());
        mensajeDTO.setIdUsuario(mensajeMuro.getUsuario().getId());
        return mensajeDTO;
    }

    public Set<MensajeDTO> get_MensajesMuro_service(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();

        return mensajeMuroRepo.findAllByUsuarioId(usuarioActual.getId()).stream()
                .map(mensaje -> {
                    MensajeDTO mensajeDTO = new MensajeDTO();
                    mensajeDTO.setUsername(mensaje.getUsuario().getUsername());
                    mensajeDTO.setFecha(mensaje.getFecha());
                    mensajeDTO.setMensaje(mensaje.getMensaje());
                    mensajeDTO.setTags(mensaje.getTags());
                    return mensajeDTO;
                })
                .collect(Collectors.toSet());
    }

    public Set<MensajeDTO> get_all_MensajeMuro_service(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();

        // Cargar explícitamente los usuarios seguidos
        Usuario usuarioConSeguidos = usuarioRepo.findWithSeguidosById(usuarioActual.getId()).orElseThrow();
        Set<Long> usuariosSeguidosIds = new HashSet<>(usuarioConSeguidos.getSeguidosIds());
        usuariosSeguidosIds.add(usuarioActual.getId()); // Agregar el ID del usuario autenticado

        Set<MensajeDTO> mensajesDTO = usuariosSeguidosIds.stream()
                .flatMap(id -> mensajeMuroRepo.findAllByUsuarioId(id).stream())
                .map(mensaje -> {
                    MensajeDTO mensajeDTO = new MensajeDTO();
                    mensajeDTO.setId(mensaje.getId());
                    mensajeDTO.setUsername(mensaje.getUsuario().getUsername());
                    mensajeDTO.setFecha(mensaje.getFecha());
                    mensajeDTO.setMensaje(mensaje.getMensaje());
                    mensajeDTO.setTags(mensaje.getTags());
                    mensajeDTO.setIdUsuario(mensaje.getUsuario().getId());
                    return mensajeDTO;
                })
                .collect(Collectors.toSet());

        return mensajesDTO;
}
}
