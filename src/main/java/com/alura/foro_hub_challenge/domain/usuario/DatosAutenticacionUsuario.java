package com.alura.foro_hub_challenge.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @NotBlank(message = "El login es obligatorio") // Valida que no sea null ni vacío
        String login,

        @NotBlank(message = "La clave es obligatoria")
        String clave
) {
}