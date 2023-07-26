package app.project.ProjectSI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/usuario/public").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(
                        (formLogin) -> formLogin
                                .successHandler(successHandler()) //url donde va a ir despues de iniciar sesion.
                )
                .sessionManagement(
                        (sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //una vez autenticado va a utilizar la misma sesion en la aplicacion.
                                .invalidSessionUrl("/login") //si la sesion es invalida va a redireccionar a esta url.
                                .maximumSessions(1) //maximo de sesiones que puede tener un usuario.
                                .expiredUrl("/login") //si la sesion expira va a redireccionar a esta url.
                                .sessionRegistry(sessionRegistry()) //registra las sesiones.
                                .and().sessionFixation(
                                        (sessionFixation) -> sessionFixation
                                                .migrateSession() //si la sesion expira va a crear una nueva sesion.
                                )
                );

        return httpSecurity.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler successHandler() {
        return((request, response, authentication) -> {
            response.sendRedirect("/usuario/home");
        });
    }

}
