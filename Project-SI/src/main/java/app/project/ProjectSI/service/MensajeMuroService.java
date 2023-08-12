package app.project.ProjectSI.service;

import app.project.ProjectSI.model.MensajeMuro;
import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.repo.IMensajeMuro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

@Service
public class MensajeMuroService {
    private final IMensajeMuro mensajeMuroRepo;

    @Autowired
    public MensajeMuroService(IMensajeMuro mensajeMuroRepo) {
        this.mensajeMuroRepo = mensajeMuroRepo;
    }

    public MensajeMuro create_mensajeMuro_service(MensajeMuro mensajeMuro){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        mensajeMuro.setUsuario(usuarioActual);
        mensajeMuro.setFecha(Instant.now());
        return mensajeMuroRepo.save(mensajeMuro);
    }

    public MensajeMuro update_mensajeMuro_service(Long id, MensajeMuro mensajeMuro){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        MensajeMuro mensajeMuroExistente = mensajeMuroRepo.findById(id).orElseThrow( ()-> new NoSuchElementException("Mensaje no encontrado"));
        mensajeMuroExistente.setMensaje(mensajeMuro.getMensaje());
        mensajeMuroExistente.setFecha(Instant.now());
        mensajeMuroExistente.setUsuario(usuarioActual);
        mensajeMuroExistente.setTags(mensajeMuro.getTags());
        return mensajeMuroRepo.save(mensajeMuroExistente);
    }

    public String delete_mensajeMuro_service(Long id){
        mensajeMuroRepo.deleteById(id);
        return "Mensaje eliminado";
    }
}
