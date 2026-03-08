package com.alura.foro_hub_challenge.infra.security;

import com.alura.foro_hub_challenge.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Obtener el token del header Authorization
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");

            // 2. Validar token y obtener el usuario (Subject)
            var nombreUsuario = tokenService.getSubject(token);

            if (nombreUsuario != null) {
                // Token valido
                var usuario = usuarioRepository.findByCorreoElectronico(nombreUsuario);

                // 3. Forzamos la autenticación en el contexto de seguridad
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); // Pasamos roles/permisos
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 4. Continuar con la cadena de filtros (hacia el siguiente filtro o el controller)
        filterChain.doFilter(request, response);
    }
}