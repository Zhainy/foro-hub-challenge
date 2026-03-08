package com.alura.foro_hub_challenge.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // "SELECT * FROM usuarios WHERE correo_electronico = ?"
    UserDetails findByCorreoElectronico(String username);
}