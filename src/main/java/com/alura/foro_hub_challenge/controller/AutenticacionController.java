package com.alura.foro_hub_challenge.controller;

import com.alura.foro_hub_challenge.domain.usuario.DatosAutenticacionUsuario;
import com.alura.foro_hub_challenge.domain.usuario.Usuario;
import com.alura.foro_hub_challenge.infra.security.DatosJWTToken;
import com.alura.foro_hub_challenge.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        // Generamos el token de autenticación con el login y la clave proporcionados
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());

        // El Manager verifica si el usuario y la contraseña (ya hasheada) coinciden en BD
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Si llegamos aquí, es que la autenticación fue exitosa. Generamos el JWT.
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}