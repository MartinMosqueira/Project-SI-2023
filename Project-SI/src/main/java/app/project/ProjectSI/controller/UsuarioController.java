package app.project.ProjectSI.controller;

import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/get/")
    public ResponseEntity<Usuario> get_usuario_controller() {
        Usuario usuario = usuarioService.get_usuario_service();
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

    //Retornar datos de la sesión del usuario logueado
    @GetMapping("/sessions")
    public ResponseEntity<?> usuariosLogueadosInfo() {
        String sessionId = "";
        User user = null;
        List<Object> sessions = sessionRegistry.getAllPrincipals();
        for (Object principal : sessions) {
            if (principal instanceof User) {
                user = (User) principal;
            }
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation sessionInformation : sessionInformations) {
                sessionId = sessionInformation.getSessionId();
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("sessionUser", user);
        return ResponseEntity.ok(response);
    }
}
