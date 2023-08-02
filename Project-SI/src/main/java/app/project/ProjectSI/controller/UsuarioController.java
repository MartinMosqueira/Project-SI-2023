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
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/find/{username}")
    public Optional<Usuario> find_username_controller(@PathVariable String username) {
        return usuarioService.find_username_service(username);
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
