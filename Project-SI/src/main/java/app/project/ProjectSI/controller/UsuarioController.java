package app.project.ProjectSI.controller;

import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.request.UsuarioDTO;
import app.project.ProjectSI.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/get/")
    public ResponseEntity<UsuarioDTO> get_usuario_controller() {
        UsuarioDTO usuario = usuarioService.get_usuario_service();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/find/{username}")
    public ResponseEntity<Usuario> find_username_controller(@PathVariable String username) {
        Usuario nameUsuario = usuarioService.find_usuario_service(username).orElseThrow();
        return ResponseEntity.ok(nameUsuario);
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> create_usuario_controller(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.create_usuario_service(usuario);
        return ResponseEntity.ok(newUsuario);
    }

    @PutMapping ("/update")
    public ResponseEntity<Usuario> update_usuario_controller(@RequestBody Usuario usuario) {
        Usuario updateUsuario = usuarioService.update_usuario_service(usuario);
        return ResponseEntity.ok(updateUsuario);
    }

    //CONTACTOS ENPOINTS

    @PostMapping("/add/contacto/{contactoID}")
    public ResponseEntity<UsuarioDTO> add_contacto_controller(@PathVariable Long contactoID) {
        UsuarioDTO usuarioDTO = usuarioService.add_contacto_service(contactoID);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/get/contactos")
    public ResponseEntity<Set<Usuario>> get_contactos_controller() {
        Set<Usuario> contactos = usuarioService.get_all_contactos_service();
        return ResponseEntity.ok(contactos);
    }

    @DeleteMapping("/delete/contacto/{contactoID}")
    public ResponseEntity<String> delete_contacto_controller(@PathVariable Long contactoID) {
        String deleteContacto = usuarioService.delete_contacto_service(contactoID);
        return ResponseEntity.ok(deleteContacto);
    }

    //SESSIONS ENDPOINTS

    @PostMapping("/add/seguido/{seguidoID}")
    public ResponseEntity<UsuarioDTO> add_seguido_controller(@PathVariable Long seguidoID) {
        UsuarioDTO usuarioDTO = usuarioService.add_seguido_service(seguidoID);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/get/seguidos")
    public ResponseEntity<Set<Usuario>> get_seguidos_controller() {
        Set<Usuario> seguidos = usuarioService.get_all_seguidos_service();
        return ResponseEntity.ok(seguidos);
    }

    @DeleteMapping("/delete/seguido/{seguidoID}")
    public ResponseEntity<String> delete_seguido_controller(@PathVariable Long seguidoID) {
        String deleteSeguido = usuarioService.delete_seguido_service(seguidoID);
        return ResponseEntity.ok(deleteSeguido);
    }
}
