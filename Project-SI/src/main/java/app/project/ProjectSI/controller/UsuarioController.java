package app.project.ProjectSI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
