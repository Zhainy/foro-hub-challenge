package com.alura.foro_hub_challenge.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull Long id,
        String nombre,
        String contrasena // Opcional: permitir cambio de clave
) {}