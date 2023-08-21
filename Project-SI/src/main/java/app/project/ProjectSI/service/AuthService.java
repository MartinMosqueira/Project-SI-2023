package app.project.ProjectSI.service;

import app.project.ProjectSI.auth.AuthResponse;
import app.project.ProjectSI.auth.LoginRequest;
import app.project.ProjectSI.auth.RegisterRequest;
import app.project.ProjectSI.model.Roles;
import app.project.ProjectSI.model.Usuario;
import app.project.ProjectSI.repo.IUsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUsuarioRepo usuarioRepo;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails user= usuarioRepo.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        Roles requestedRole = Roles.valueOf(registerRequest.getRol());
        Usuario usuario = Usuario.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .fechaNacimiento(registerRequest.getFechaNacimiento())
                .rol(requestedRole)
                .build();
        usuarioRepo.save(usuario);
        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }
}
