package app.project.ProjectSI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping("/home")
    public String home() {
        return "Hola Mundo";
    }

    @RequestMapping("/public")
    public String login() {
        return "login";
    }

    @RequestMapping("/sessions")
    public ResponseEntity<?> usuariosLogueadosInfo() {
        return ResponseEntity.ok(sessionRegistry.getAllPrincipals());
    }
}
